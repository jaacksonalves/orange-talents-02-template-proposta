package br.com.zup.orange.desafioproposta.proposta;

import br.com.zup.orange.desafioproposta.proposta.analise.AnaliseProposta;
import br.com.zup.orange.desafioproposta.proposta.analise.AnaliseResponse;
import br.com.zup.orange.desafioproposta.proposta.analise.AnaliseStatus;
import feign.FeignException;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private AnaliseProposta analiseProposta;

    @Transactional
    @PostMapping
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder uriComponentsBuilder) {
        //verifica se o documento está cadastrado ou não no banco de dados, pois não pode haver mais de uma proposta pro mesmo documento
        if (request.validaDocumentoNaoCadastrado(em)) {
            Proposta novaProposta = request.toModel(em);
            em.persist(novaProposta);
            URI uri = uriComponentsBuilder.path("/api/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri();

            try {
                AnaliseResponse analiseResponse = analiseProposta.analiseStatus(novaProposta.toAnaliseRequest());
                novaProposta.setStatus(PropostaStatus.analiseToProposta(analiseResponse.getStatusResultado()));
                em.merge(novaProposta);
                return ResponseEntity.created(uri).body("Elegível");
            } catch (Exception e) {
                novaProposta.setStatus(PropostaStatus.NAO_ELEGIVEL);
                return ResponseEntity.unprocessableEntity().body("Não Elegível");
            }

        }
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe proposta cadastrada para o documento inserido: " + request.getDocumento());
    }


}