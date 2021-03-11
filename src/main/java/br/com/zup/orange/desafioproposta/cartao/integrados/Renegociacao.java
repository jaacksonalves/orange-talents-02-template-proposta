package br.com.zup.orange.desafioproposta.cartao.integrados;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
public class Renegociacao {

    private Long idRenegociacao;
    private Integer quantidadeRenegociacao;
    private BigDecimal valorRenegociacao;
    private LocalDateTime renegociacaoCriadaEm;

    public Renegociacao(Long id, Integer quantidade, BigDecimal valor, LocalDateTime dataDeCriacao) {
        this.idRenegociacao = id;
        this.quantidadeRenegociacao = quantidade;
        this.valorRenegociacao = valor;
        this.renegociacaoCriadaEm = dataDeCriacao;
    }

    public Renegociacao() {

    }
}
