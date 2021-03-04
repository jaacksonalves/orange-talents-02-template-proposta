package br.com.zup.orange.desafioproposta.controllers;

import br.com.zup.orange.desafioproposta.proposta.NovaPropostaRequest;
import br.com.zup.orange.desafioproposta.proposta.Proposta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class PropostaControllerTest {

    @Autowired
    MockMvc mockMvc;
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ObjectMapper mapper;

    private String toJson(Object form) throws JsonProcessingException {
        return mapper.writeValueAsString(form);
    }


    @Test
    @DisplayName("Deve criar proposta contendo, cpf ou cnpj, email, nome, endereço, salário. nenhum campo pode ser branco ou nulo, e documento/email tem de ser válidos" +
            "deve retornar status 201 quando criado E devolve header com a location do objeto criado")
    public void deveCriarProposta() throws Exception {
        NovaPropostaRequest novaPropostaRequestCpf = new NovaPropostaRequest("10648515680", "cpf@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));
        NovaPropostaRequest novaPropostaRequestCnpj = new NovaPropostaRequest("78506687000129", "cnpj@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));

        //Cadastra nova proposta completa e CPF como documento
        String locationCpf = mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(novaPropostaRequestCpf)))
                .andExpect(status().is(201)).andReturn().getResponse().getHeader("Location");

        //Cadastra nova proposta completa e CNPJ como documento
        String locationCnpj = mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(novaPropostaRequestCnpj)))
                .andExpect(status().is(201)).andReturn().getResponse().getHeader("Location");


        Proposta novaPropostaCpf = (Proposta) em.createQuery("select p from Proposta p where p.email=:email").setParameter("email", "cpf@email.com").getResultList().get(0);
        Proposta novaPropostaCnpj = (Proposta) em.createQuery("select p from Proposta p where p.email=:email").setParameter("email", "cnpj@email.com").getResultList().get(0);

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

        NovaPropostaRequest novaPropostaRequestCpf = new NovaPropostaRequest("10648515680", "cpf@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));
        NovaPropostaRequest novaPropostaRequestCnpj = new NovaPropostaRequest("78506687000129", "cnpj@email.com", "Jackson Alves", "Endereco completo", new BigDecimal(2500));

        em.persist(novaPropostaRequestCpf.toModel(em));
        em.persist(novaPropostaRequestCnpj.toModel(em));

        //Tenta cadastrar nova proposta com Cpf ja cadastrado
        mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(novaPropostaRequestCpf)))
                .andExpect(status().is(422));

        //Tenta cadastrar nova proposta com Cnpj ja cadastrado
        mockMvc.perform(post("/api/propostas").contentType(MediaType.APPLICATION_JSON).content(toJson(novaPropostaRequestCnpj)))
                .andExpect(status().is(422));


        List<?> listaPropostasCpf = em.createQuery("select p from Proposta p where p.email=:email").setParameter("email", "cpf@email.com").getResultList();
        List<?> listaPropostasCnpj = em.createQuery("select p from Proposta p where p.email=:email").setParameter("email", "cnpj@email.com").getResultList();

        assertTrue(listaPropostasCpf.size() <= 1);
        assertTrue(listaPropostasCnpj.size() <= 1);

    }


}


