package br.com.zup.orange.desafioproposta.biometria;

import br.com.zup.orange.desafioproposta.cartao.Cartao;

import javax.persistence.*;

@Entity
public class Biometria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String impressaoDigital;
    @ManyToOne
    private Cartao cartao;


    public Biometria(String impressaoDigital, Cartao cartao) {
        this.impressaoDigital = impressaoDigital;
        this.cartao = cartao;
    }

    @Deprecated
    public Biometria() {
    }

    public Long getId() {
        return id;
    }
}
