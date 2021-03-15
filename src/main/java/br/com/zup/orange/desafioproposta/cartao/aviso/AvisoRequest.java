package br.com.zup.orange.desafioproposta.cartao.aviso;

import java.time.LocalDate;

public class AvisoRequest {

    private final LocalDate validoAte;
    private final String destino;

    public AvisoRequest(LocalDate validAte, String destino) {
        this.validoAte = validAte;
        this.destino = destino;
    }

    public AvisoViagem montaAviso(){
        return new AvisoViagem(validoAte, destino);
    }
}
