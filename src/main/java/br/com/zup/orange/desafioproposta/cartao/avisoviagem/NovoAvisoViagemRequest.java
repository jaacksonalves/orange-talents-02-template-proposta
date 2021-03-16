package br.com.zup.orange.desafioproposta.cartao.avisoviagem;

import br.com.zup.orange.desafioproposta.cartao.Cartao;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NovoAvisoViagemRequest {

    @NotBlank
    private String destino;
    @NotNull
    @FutureOrPresent
    private LocalDate validoAte;

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public AvisoViagem toModel(Cartao cartao, String ipSolicitante, String agent) {
        return new AvisoViagem(cartao, validoAte, destino, ipSolicitante, agent);
    }
}
