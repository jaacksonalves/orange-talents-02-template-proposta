package br.com.zup.orange.desafioproposta.cartao.integrados;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class Aviso {
    private LocalDate validAte;
    private String destino;

    public Aviso(LocalDate validAte, String destino) {
        this.validAte = validAte;
        this.destino = destino;
    }

    @Deprecated
    public Aviso() {
    }
}
