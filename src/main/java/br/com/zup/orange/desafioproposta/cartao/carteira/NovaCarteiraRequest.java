package br.com.zup.orange.desafioproposta.cartao.carteira;

import br.com.zup.orange.desafioproposta.cartao.Cartao;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NovaCarteiraRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String carteira;

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }

    public Carteira toModel(Cartao cartao, String id) {
        return new Carteira(id, email, carteira, cartao);
    }
}
