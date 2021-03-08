package br.com.zup.orange.desafioproposta.cartao;


import br.com.zup.orange.desafioproposta.proposta.analise.AnalisePropostaRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cartao", url = "http://localhost:8888/api")
public interface ConectorCartao {

    @PostMapping("/cartoes")
    CartaoResposta cartaoResposta (AnalisePropostaRequest request);

}
