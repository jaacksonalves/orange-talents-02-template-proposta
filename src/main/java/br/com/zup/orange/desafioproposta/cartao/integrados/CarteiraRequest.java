package br.com.zup.orange.desafioproposta.cartao.integrados;

import java.time.LocalDateTime;

public class CarteiraRequest {

    private final Long id;
    private final String email;
    private final LocalDateTime associadaEm;
    private final String emissor;

    public CarteiraRequest(Long id, String email, LocalDateTime associadaEm, String emissor) {
        this.id = id;
        this.email = email;
        this.associadaEm = associadaEm;
        this.emissor = emissor;
    }

    public Carteira montaCarteira() {
        return new Carteira(id, email, associadaEm, emissor);
    }
}
