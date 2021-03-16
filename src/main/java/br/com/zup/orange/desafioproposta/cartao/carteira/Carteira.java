package br.com.zup.orange.desafioproposta.cartao.carteira;

import br.com.zup.orange.desafioproposta.cartao.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idExterno;
    private String email;
    @Column(updatable = false)
    private final LocalDateTime associadaEm = LocalDateTime.now();
    private String emissorCarteira;
    @ManyToOne
    private Cartao cartao;

    public Carteira(String idExterno, String email, String emissorCarteira, Cartao cartao) {
        this.idExterno = idExterno;
        this.email = email;
        this.emissorCarteira = emissorCarteira;
        this.cartao = cartao;
    }

    @Deprecated
    public Carteira() {
    }

    public Long getId() {
        return id;
    }
}
