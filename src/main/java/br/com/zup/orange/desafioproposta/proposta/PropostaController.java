package br.com.zup.orange.desafioproposta.proposta;

import br.com.zup.orange.desafioproposta.proposta.analise.AnalisePropostaRequest;
import br.com.zup.orange.desafioproposta.proposta.analise.AnalisePropostaResponse;
import br.com.zup.orange.desafioproposta.proposta.analise.ConectorAnaliseProposta;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private ConectorAnaliseProposta conectorAnaliseProposta;


    /*
    Cadastra nova proposta no sistema
     */
    @Transactional
    @PostMapping
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder uriComponentsBuilder) {
        //verifica se o documento está cadastrado ou não no banco de dados, pois não pode haver mais de uma proposta pro mesmo documento
        if (propostaRepository.existsByDocumento(request.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe proposta cadastrada para esse documento!");
        }
        Proposta novaProposta = request.toModel();
        propostaRepository.save(novaProposta);
        URI uri = uriComponentsBuilder.path("/api/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri();

        try {
            AnalisePropostaResponse respostaAnalise = conectorAnaliseProposta.analiseStatus(new AnalisePropostaRequest(novaProposta));
            novaProposta.setStatus(PropostaStatus.analiseToProposta(respostaAnalise.getStatusResultado()));

            return ResponseEntity.created(uri).build();
        } catch (FeignException e) {
            novaProposta.setStatus(PropostaStatus.NAO_ELEGIVEL);

            return ResponseEntity.unprocessableEntity().location(uri).body("Proposta Não Elegível");
        }
    }


    /*
    Exibe uma proposta específica pelo ID
     */
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> consultaProposta(@PathVariable("id") Long idProposta) {
        Proposta proposta = propostaRepository.findById(idProposta).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proposta não encontrada"));

        return ResponseEntity.ok(new PropostaResponse(proposta).toString());

    }


    //A Classe que cria e atrela um numero de cartão a uma proposta foi criada separadamente: AtrelaCartaoAProposta.class


}