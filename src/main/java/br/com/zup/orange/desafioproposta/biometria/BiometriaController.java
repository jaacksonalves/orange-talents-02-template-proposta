package br.com.zup.orange.desafioproposta.biometria;

import br.com.zup.orange.desafioproposta.cartao.Cartao;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/biometria")
public class BiometriaController {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @PostMapping("/{idCartao}")
    public ResponseEntity<?> cadastraBiometria(@PathVariable("idCartao") Long idCartao,
                                               @Valid @RequestBody NovaBiometriaRequest request,
                                               UriComponentsBuilder uriComponentsBuilder) {
        //Verifica se o Id do cartão inserido na URL é valido
        Cartao cartao = Optional.ofNullable(em.find(Cartao.class, idCartao))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cartão não encontrado"));

        //Verifica se a impressão digital inserida no corpo é Base64
        if (!Base64.isBase64(request.getImpressaoDigital())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Deve ser inserido uma Base64 válida!");
        }

        Biometria novaBiometria = request.toModel(cartao);
        em.persist(novaBiometria);

        URI uri = uriComponentsBuilder.path("/api/biometria/{id}").buildAndExpand(novaBiometria.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }
}
