package br.com.zup.orange.desafioproposta.proposta.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "analise", url = "http://localhost:9999")
public interface ConectorAnaliseProposta {

    @PostMapping("/api/solicitacao")
    AnaliseResponse analiseStatus(AnalisePropostaRequest request);

}
