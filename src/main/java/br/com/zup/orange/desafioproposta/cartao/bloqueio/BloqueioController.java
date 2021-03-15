package br.com.zup.orange.desafioproposta.cartao.bloqueio;

import br.com.zup.orange.desafioproposta.cartao.Cartao;
import br.com.zup.orange.desafioproposta.cartao.CartaoRepository;
import br.com.zup.orange.desafioproposta.cartao.ConectorCartao;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/cartao/bloqueio")
public class BloqueioController {

    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private ConectorCartao conectorCartao;
    @Autowired
    BloqueioRepository bloqueioRepository;

    @Transactional
    @PostMapping("/{id}")
    public ResponseEntity<?> bloqueiaCartao(@PathVariable("id") Long idCartao,
                                            @Valid @RequestBody BloqueioCartaoRequest request,
                                            HttpServletRequest servletRequest) {

        Cartao cartao = cartaoRepository.findById(idCartao).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        String numeroCartao = cartao.getNumeroCartao();

        String ipCliente = servletRequest.getRemoteAddr();

        try {
            BloqueioCartaoResponse bloqueioCartaoResponse = conectorCartao.bloqueiaCartao(numeroCartao, request);
            if (bloqueioCartaoResponse.getResultado().equals("BLOQUEADO")) {
                cartao.toBloqueio(ipCliente, request.getSistemaResponsavel());
                cartaoRepository.save(cartao);
            }
        } catch (FeignException e) {
            return ResponseEntity.unprocessableEntity().body("Cartão já bloqueado");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ops! Erro inesperado");
        }

        return ResponseEntity.ok().body("Cartão bloqueado");
    }
}
