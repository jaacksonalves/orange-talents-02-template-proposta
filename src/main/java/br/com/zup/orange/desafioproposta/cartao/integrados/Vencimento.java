package br.com.zup.orange.desafioproposta.cartao.integrados;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Vencimento {

    private Long idVencimento;
    private Integer diaVencimento;
    private LocalDateTime vencimentoCriadoEm;


    public Vencimento(Long id, Integer dia, LocalDateTime dataDeCriacao) {
        this.idVencimento = id;
        this.diaVencimento = dia;
        this.vencimentoCriadoEm = dataDeCriacao;
    }

    public Vencimento() {

    }
}
