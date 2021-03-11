package br.com.zup.orange.desafioproposta.cartao.integrados;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Parcela {

    private Long idParcela;
    private Integer quantidadeParcelas;
    private BigDecimal valorParcelas;

    public Parcela(Long id, Integer quantidade, BigDecimal valor) {
        this.idParcela = id;
        this.quantidadeParcelas = quantidade;
        this.valorParcelas = valor;
    }

    public Parcela() {

    }
}
