package br.com.zup.orange.desafioproposta.cartao.integrados;

import java.math.BigDecimal;

public class ParcelaRequest {

    private final Long id;
    private final Integer quantidade;
    private final BigDecimal valor;

    public ParcelaRequest(Long id, Integer quantidade, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Parcela montaParcela(){
        return new Parcela(id, quantidade, valor);
    }
}
