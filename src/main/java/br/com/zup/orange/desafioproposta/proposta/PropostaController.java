package br.com.zup.orange.desafioproposta.proposta;

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

    @Transactional
    @PostMapping
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder uriComponentsBuilder) {
        if (request.validaDocumentoNaoCadastrado(em)) {
            Proposta novaProposta = request.toModel(em);
            em.persist(novaProposta);
            URI uri = uriComponentsBuilder.path("/api/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri();

            return ResponseEntity.status(201).location(uri).build();
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Já existe proposta cadastrada para o documento inserido: " + request.getDocumento());
        //throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe proposta cadastrada para o documento inserido: " + request.getDocumento());
    }
}
