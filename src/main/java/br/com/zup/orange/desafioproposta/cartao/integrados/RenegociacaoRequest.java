package br.com.zup.orange.desafioproposta.cartao.integrados;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RenegociacaoRequest {

    private final Long id;
    private final Integer quantidade;
    private final BigDecimal valor;
    private final LocalDateTime dataDeCriacao;

    public RenegociacaoRequest(Long id, Integer quantidade, BigDecimal valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Renegociacao montaRenegociacao(){
        return new Renegociacao(id, quantidade, valor, dataDeCriacao);
    }
}
