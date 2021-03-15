package br.com.zup.orange.desafioproposta.cartao.aviso;

import br.com.zup.orange.desafioproposta.cartao.Cartao;
import br.com.zup.orange.desafioproposta.cartao.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/viagem")
public class AvisoViagemController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AvisoViagemRepository avisoViagemRepository;


    @PostMapping("/{idCartao}")
    public ResponseEntity<?> cadastraAvisoViagem(@PathVariable("idCartao") Long idCartao
            , HttpServletRequest httpServletRequest
            , @Valid @RequestBody NovoAvisoViagemRequest request) {

        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        String ipSolicitante = httpServletRequest.getRemoteAddr();

        AvisoViagem novoAvisoViagem = request.toModel(cartao, ipSolicitante);

        avisoViagemRepository.save(novoAvisoViagem);

        return ResponseEntity.ok().build();
    }
}
