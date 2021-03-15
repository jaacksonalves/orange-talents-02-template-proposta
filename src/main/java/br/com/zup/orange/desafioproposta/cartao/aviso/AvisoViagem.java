package br.com.zup.orange.desafioproposta.cartao.aviso;

import br.com.zup.orange.desafioproposta.cartao.Cartao;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Cartao cartao;
    private LocalDate dataTerminoViagem;
    private String destino;
    private String ipSolicitante;
    @Column(updatable = false)
    private final LocalDateTime instanteAviso = LocalDateTime.now();

    public AvisoViagem(Cartao cartao, LocalDate dataTerminoViagem, String destino, String ipSolicitante) {
        this.cartao = cartao;
        this.dataTerminoViagem = dataTerminoViagem;
        this.destino = destino;
        this.ipSolicitante = ipSolicitante;
    }

    public AvisoViagem(LocalDate validAte, String destino) {
        this.dataTerminoViagem = validAte;
        this.destino = destino;
    }

    @Deprecated
    public AvisoViagem() {
    }


}
