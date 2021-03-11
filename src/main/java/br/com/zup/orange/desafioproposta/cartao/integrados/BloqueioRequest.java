package br.com.zup.orange.desafioproposta.cartao.integrados;

import java.time.LocalDateTime;

public class BloqueioRequest {

    private final Long id;
    private final LocalDateTime bloqueadoEm;
    private final String sistemaResponsvel;
    private final boolean ativo;

    public BloqueioRequest(Long id, LocalDateTime bloqueadoEm, String sistemaResponsvel, boolean ativo) {
        this.id = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsvel = sistemaResponsvel;
        this.ativo = ativo;
    }

    public Bloqueio montaBloqueio() {
        return new Bloqueio(id, bloqueadoEm, sistemaResponsvel, ativo);
    }


    public Long getId() {
        return id;
    }

    public LocalDateTime getBloqueadoEm() {
        return bloqueadoEm;
    }

    public String getSistemaResponsvel() {
        return sistemaResponsvel;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
