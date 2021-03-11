package br.com.zup.orange.desafioproposta.cartao.integrados;

import java.time.LocalDateTime;

public class VencimentoRequest {

    private final Long id;
    private final Integer dia;
    private final LocalDateTime dataDeCriacao;

    public VencimentoRequest(Long id, Integer dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Vencimento montaVencimento(){
        return new Vencimento(id, dia, dataDeCriacao);
    }
}
