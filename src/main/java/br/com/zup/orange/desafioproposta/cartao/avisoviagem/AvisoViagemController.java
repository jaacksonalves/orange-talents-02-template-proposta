package br.com.zup.orange.desafioproposta.cartao.avisoviagem;

import br.com.zup.orange.desafioproposta.cartao.Cartao;
import br.com.zup.orange.desafioproposta.cartao.CartaoRepository;
import br.com.zup.orange.desafioproposta.cartao.ConectorAvisoViagemResponse;
import br.com.zup.orange.desafioproposta.cartao.ConectorCartao;
import feign.FeignException;
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
    @Autowired
    private ConectorCartao conectorCartao;


    @PostMapping("/{idCartao}")
    public ResponseEntity<?> cadastraAvisoViagem(@PathVariable("idCartao") Long idCartao
            , HttpServletRequest httpServletRequest
            , @Valid @RequestBody NovoAvisoViagemRequest request
            , @RequestHeader("user-agent") String agent) {

        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));

        String ipSolicitante = httpServletRequest.getRemoteAddr();

        AvisoViagem novoAvisoViagem = request.toModel(cartao, ipSolicitante, agent);

        try {
            ConectorAvisoViagemResponse conectorAvisoViagemResponse = conectorCartao.avisoViagem(cartao.getNumeroCartao(), request);
            if (conectorAvisoViagemResponse.getResultado().equals("CRIADO")) {
                avisoViagemRepository.save(novoAvisoViagem);

            }
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aviso já enviado");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado");
        }
        return ResponseEntity.status(201).build();
    }


}
