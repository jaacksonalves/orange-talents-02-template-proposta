package br.com.zup.orange.desafioproposta.cartao.aviso;

import br.com.zup.orange.desafioproposta.cartao.Cartao;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NovoAvisoViagemRequest {

    @NotBlank
    private String destinoViagem;
    @NotNull
    @FutureOrPresent
    private LocalDate dataTerminoViagem;

    public String getDestinoViagem() {
        return destinoViagem;
    }

    public LocalDate getDataTerminoViagem() {
        return dataTerminoViagem;
    }

    public AvisoViagem toModel(Cartao cartao, String ipSolicitante) {
        return new AvisoViagem(cartao, dataTerminoViagem, destinoViagem, ipSolicitante);
    }
}
