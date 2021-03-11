package br.com.zup.orange.desafioproposta.cartao.integrados;

import java.time.LocalDate;

public class AvisoRequest {

    private final LocalDate validAte;
    private final String destino;

    public AvisoRequest(LocalDate validAte, String destino) {
        this.validAte = validAte;
        this.destino = destino;
    }

    public Aviso montaAviso(){
        return new Aviso(validAte, destino);
    }
}
