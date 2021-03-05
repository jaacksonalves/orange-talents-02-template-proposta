package br.com.zup.orange.desafioproposta.proposta;

import br.com.zup.orange.desafioproposta.proposta.analise.AnaliseRequest;
import br.com.zup.orange.desafioproposta.proposta.analise.AnaliseResponse;
import br.com.zup.orange.desafioproposta.proposta.analise.AnaliseStatus;

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

    public AnaliseRequest toAnaliseRequest() {
        return new AnaliseRequest(this.id, this.documento, this.nome);
    }

    public void setStatus(PropostaStatus status) {
        this.status = status;
    }
}
