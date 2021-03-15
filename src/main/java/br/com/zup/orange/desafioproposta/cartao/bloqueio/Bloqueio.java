package br.com.zup.orange.desafioproposta.cartao.bloqueio;

import br.com.zup.orange.desafioproposta.cartao.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBloqueio;
    private final LocalDateTime bloqueadoEm = LocalDateTime.now();
    private String sistemaResponsvel;
    private boolean bloqueioAtivo;
    @ManyToOne
    private Cartao cartao;
    private String ipCliente;

    public Bloqueio(Long id, LocalDateTime bloqueadoEm, String sistemaResponsvel, boolean bloqueioAtivo) {
        this.idBloqueio = id;
        this.sistemaResponsvel = sistemaResponsvel;
        this.bloqueioAtivo = bloqueioAtivo;
    }

    public Bloqueio() {
    }

    public Bloqueio(String ipCliente, String sistemaResponsavel, Cartao cartao) {
        this.ipCliente = ipCliente;
        this.sistemaResponsvel = sistemaResponsavel;
        this.cartao = cartao;
        this.bloqueioAtivo = true;
    }
}
