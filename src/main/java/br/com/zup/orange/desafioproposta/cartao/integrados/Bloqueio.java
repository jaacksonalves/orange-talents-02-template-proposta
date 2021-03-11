package br.com.zup.orange.desafioproposta.cartao.integrados;

import br.com.zup.orange.desafioproposta.cartao.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {
    @Id
    private Long idBloqueio;
    private LocalDateTime bloqueadoEm;
    private String sistemaResponsvel;
    private boolean bloqueioAtivo;
    @ManyToOne
    private Cartao cartao;

    public Bloqueio(Long id, LocalDateTime bloqueadoEm, String sistemaResponsvel, boolean ativo) {
        this.idBloqueio = id;
        this.bloqueadoEm = bloqueadoEm;
        this.sistemaResponsvel = sistemaResponsvel;
        this.bloqueioAtivo = ativo;
    }

    public Bloqueio() {
    }
}
