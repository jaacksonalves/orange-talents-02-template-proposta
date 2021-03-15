package br.com.zup.orange.desafioproposta.cartao;

import br.com.zup.orange.desafioproposta.biometria.Biometria;
import br.com.zup.orange.desafioproposta.cartao.bloqueio.Bloqueio;
import br.com.zup.orange.desafioproposta.proposta.Proposta;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cartoes")
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCartao;
    private LocalDateTime emitidoEm;
    private String titular;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<Bloqueio> bloqueios;
    private BigDecimal limite;
    @OneToOne(mappedBy = "cartao")
    private Proposta proposta;
    @OneToMany(mappedBy = "cartao")
    private List<Biometria> biometrias;


    public Cartao(String numeroCartao, LocalDateTime emitidoEm, String titular, List<Bloqueio> bloqueios, BigDecimal limite, Proposta proposta) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.limite = limite;
        this.proposta = proposta;
    }

    @Deprecated
    public Cartao() {
    }

    public Long getId() {
        return id;
    }

    public void toBloqueio(String ipCliente, String sistemaResponsavel) {
        Bloqueio bloqueio = new Bloqueio(ipCliente, sistemaResponsavel, this);
        this.bloqueios.add(bloqueio);

    }

    public String getNumeroCartao() {
        return numeroCartao;
    }
}
