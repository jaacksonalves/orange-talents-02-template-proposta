package br.com.zup.orange.desafioproposta.cartao;


import br.com.zup.orange.desafioproposta.cartao.bloqueio.BloqueioCartaoRequest;
import br.com.zup.orange.desafioproposta.cartao.bloqueio.BloqueioCartaoResponse;
import br.com.zup.orange.desafioproposta.proposta.analise.AnalisePropostaRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cartao", url = "${api.cartao}")
public interface ConectorCartao {

    @PostMapping("/cartoes")
    ConectorCartaoResponse apiToCartaoResposta(AnalisePropostaRequest request);

    @PostMapping("/cartoes/{id}/bloqueios")
    BloqueioCartaoResponse bloqueiaCartao(@PathVariable("id") String idCartao, BloqueioCartaoRequest request);
}
