package br.com.zup.orange.desafioproposta.cartao.integrados;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Carteira {

    private Long idCarteira;
    private String emailCarteira;
    private LocalDateTime carteiraAssociadaEm;
    private String emissorCarteira;

    public Carteira(Long id, String email, LocalDateTime associadaEm, String emissor) {
        this.idCarteira = id;
        this.emailCarteira = email;
        this.carteiraAssociadaEm = associadaEm;
        this.emissorCarteira = emissor;
    }

    public Carteira() {

    }
}
