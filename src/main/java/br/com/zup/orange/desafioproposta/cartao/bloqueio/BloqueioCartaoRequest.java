package br.com.zup.orange.desafioproposta.cartao.bloqueio;

import javax.validation.constraints.NotBlank;

public class BloqueioCartaoRequest {

    @NotBlank
    private String sistemaResponsavel;

    public BloqueioCartaoRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    @Deprecated
    public BloqueioCartaoRequest() {
    }
}
