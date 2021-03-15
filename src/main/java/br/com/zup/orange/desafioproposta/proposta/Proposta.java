package br.com.zup.orange.desafioproposta.proposta;

import br.com.zup.orange.desafioproposta.cartao.Cartao;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String documento;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private PropostaStatus status;
    @OneToOne
    private Cartao cartao;
    public String numeroCartao;



    public Proposta(String documento, String email, String nome, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    @Deprecated
    public Proposta() {
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public PropostaStatus getStatus() {
        return status;
    }

    public void updateStatus(PropostaStatus status) {
        this.status = status;
    }

    public void updateCartao(Cartao cartao) {
        this.cartao = cartao;
        this.numeroCartao = cartao.getNumeroCartao();
    }

    public Cartao getCartao() {
        return cartao;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }
}
