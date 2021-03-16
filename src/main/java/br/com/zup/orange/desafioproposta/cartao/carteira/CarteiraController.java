package br.com.zup.orange.desafioproposta.cartao.carteira;

import br.com.zup.orange.desafioproposta.cartao.Cartao;
import br.com.zup.orange.desafioproposta.cartao.CartaoRepository;
import br.com.zup.orange.desafioproposta.cartao.ConectorCartao;
import br.com.zup.orange.desafioproposta.cartao.ConectorCarteiraResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/carteiras")
public class CarteiraController {

    @Autowired
    private CarteiraRepository carteiraRepository;
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private ConectorCartao conectorCartao;

    @PostMapping("{idCartao}")
    public ResponseEntity<?> cadastra(@PathVariable("idCartao") Long idCartao,
                                      @Valid @RequestBody NovaCarteiraRequest request,
                                      UriComponentsBuilder uriComponentsBuilder) {

        Cartao cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));


        try {
            ConectorCarteiraResponse carteiraResponse = conectorCartao.carteiras(cartao.getNumeroCartao(), request);
            Carteira novaCarteira = request.toModel(cartao, carteiraResponse.getId());
            carteiraRepository.save(novaCarteira);
            URI uri = uriComponentsBuilder.path("/api/carteiras/" + idCartao + "{id}").buildAndExpand(novaCarteira.getId()).toUri();
            return ResponseEntity.created(uri).build();

        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Carteira já cadastrada");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ops, deu ruim :( ");
        }

    }
}
