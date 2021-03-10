package br.com.zup.orange.desafioproposta.proposta.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "analise", url = "${api.analise}")
public interface ConectorAnaliseProposta {

    @PostMapping("/solicitacao")
    AnalisePropostaResponse analiseStatus(AnalisePropostaRequest request);

}
