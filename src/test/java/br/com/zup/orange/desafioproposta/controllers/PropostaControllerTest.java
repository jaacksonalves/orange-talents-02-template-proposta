package br.com.zup.orange.desafioproposta.controllers;

import br.com.zup.orange.desafioproposta.proposta.NovaPropostaRequest;
import br.com.zup.orange.desafioproposta.proposta.Proposta;
import br.com.zup.orange.desafioproposta.proposta.PropostaRepository;
import br.com.zup.orange.desafioproposta.proposta.PropostaResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PropostaControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private PropostaRepository repository;
    @Autowired
    private ObjectMapper mapper;

    private String toJson(Object form) throws JsonProcessingException {
        return mapper.writeValueAsString(form);
    }


    @Test
    @DisplayName("Deve criar proposta contendo, cpf ou cnpj, email, nome, endereço, salário. nenhum campo pode ser branco ou nulo, e documento/email tem de ser válidos" +
            "deve retornar status 201 quando criado E devolve header com a location do objeto criado")
    public void deveCriarProposta() throws Exception {
        NovaPropostaRequest novaPropostaRequestCpf = new NovaPropostaRequest("18270623040", "cpf@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));
        NovaPropostaRequest novaPropostaRequestCnpj = new NovaPropostaRequest("78506687000129", "cnpj@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));

        //Cadastra nova proposta completa e CPF como documento
        String locationCpf = mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(novaPropostaRequestCpf)))
                .andExpect(status().is(201)).andReturn().getResponse().getHeader("Location");

        //Cadastra nova proposta completa e CNPJ como documento
        String locationCnpj = mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(novaPropostaRequestCnpj)))
                .andExpect(status().is(201)).andReturn().getResponse().getHeader("Location");


        Proposta novaPropostaCpf = repository.findByEmail(novaPropostaRequestCpf.getEmail());
        Proposta novaPropostaCnpj = repository.findByEmail(novaPropostaRequestCnpj.getEmail());

        assertEquals(locationCpf, "http://localhost/api/propostas/" + novaPropostaCpf.getId());
        assertEquals(locationCnpj, "http://localhost/api/propostas/" + novaPropostaCnpj.getId());
        assertNotNull(novaPropostaCnpj);
        assertNotNull(novaPropostaCpf);
        assertEquals(novaPropostaRequestCpf.getDocumento(), novaPropostaCpf.getDocumento());
        assertEquals(novaPropostaRequestCnpj.getDocumento(), novaPropostaCnpj.getDocumento());
    }


    @Test
    @DisplayName("Não deve criar propostas com dados de documentos invalidos, sendo cpf ou cnpj, devolver status 400")
    public void naoDeveCriarPropostasComCpfOuCnpjInvalidos() throws Exception {
        NovaPropostaRequest novaPropostaRequestCpfInvalido = new NovaPropostaRequest("12345678910", "cpf@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));
        NovaPropostaRequest novaPropostaRequestCnpjInvalido = new NovaPropostaRequest("12345678910123", "cnpj@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));

        //Tenta cadastrar proposta com CPF invalido
        mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(novaPropostaRequestCpfInvalido)))
                .andExpect(status().is(400));

        //Tenta cadastrar proposta com CNPJ invalido
        mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(novaPropostaRequestCnpjInvalido)))
                .andExpect(status().is(400));

    }

    @Test
    @DisplayName("Não deve criar propostas com documento duplicado, deve retornar 422")
    public void naoDeveCriarPropostaComDocumentoJaCadastrado() throws Exception {

        NovaPropostaRequest novaPropostaRequestCpf = new NovaPropostaRequest("18270623040", "cpf@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));
        NovaPropostaRequest novaPropostaRequestCnpj = new NovaPropostaRequest("78506687000129", "cnpj@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));

        repository.save(novaPropostaRequestCpf.toModel());
        repository.save(novaPropostaRequestCnpj.toModel());

        //Tenta cadastrar nova proposta com Cpf ja cadastrado
        mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(novaPropostaRequestCpf)))
                .andExpect(status().is(422));

        //Tenta cadastrar nova proposta com Cnpj ja cadastrado
        mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(novaPropostaRequestCnpj)))
                .andExpect(status().is(422));


    }

    @Test
    @DisplayName("Deve retornar informações de uma proposta, pelo id inserido na URL. Retorno 200.")
    public void deveRetornarInformacoesDaPropostaPorId() throws Exception {

        NovaPropostaRequest novaPropostaRequestCpf = new NovaPropostaRequest("18270623040", "cpf@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));
        Proposta proposta = novaPropostaRequestCpf.toModel();
        repository.save(proposta);


        String contentAsString = mockMvc.perform(get("/api/propostas/" + proposta.getId()))
                .andExpect(status().is(200)).andReturn().getResponse().getContentAsString();

        PropostaResponse propostaResponse = new PropostaResponse(proposta);

        assertEquals(propostaResponse.toString(), contentAsString);

    }


    @Test
    @DisplayName("Não deve retornar informações de proposta com Id inserido na URL inexistente. Status 404")
    public void naoDeveRetornarInformacoesPropostaComIdInexistente() throws Exception {

        mockMvc.perform(get("/api/propostas/100000000")).andExpect(status().is(404));


    }


}


