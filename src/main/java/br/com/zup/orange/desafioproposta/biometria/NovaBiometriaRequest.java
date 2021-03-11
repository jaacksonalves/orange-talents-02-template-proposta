package br.com.zup.orange.desafioproposta.biometria;

import br.com.zup.orange.desafioproposta.cartao.Cartao;

import javax.validation.constraints.NotBlank;

public class NovaBiometriaRequest {

    @NotBlank
    private String impressaoDigital;

    public Biometria toModel(Cartao cartao) {
        return new Biometria(impressaoDigital, cartao);
    }

    public String getImpressaoDigital() {
        return impressaoDigital;
    }
}
