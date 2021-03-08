package br.com.zup.orange.desafioproposta.proposta;

import br.com.zup.orange.desafioproposta.compartilhado.CpfOuCnpj;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public class NovaPropostaRequest {

    @CpfOuCnpj(message = "Deve preencher com um CPF ou CNPJ válido!")
    @NotBlank(message = "Precisa ser preenchido")
    private String documento;
    @NotBlank(message = "Precisa ser preenchido")
    @Email(message = "Deve preencher com email válido")
    private String email;
    @NotBlank(message = "Precisa ser preenchido")
    private String nome;
    @NotBlank(message = "Precisa ser preenchido")
    private String endereco;
    @NotNull(message = "Precisa ser preenchido")
    @Positive(message = "Não pode preencher com valor negativo")
    private BigDecimal salario;

    public NovaPropostaRequest(@NotBlank(message = "Precisa ser preenchido") String documento, @NotBlank(message = "Precisa ser preenchido") @Email(message = "Deve preencher com email válido") String email, @NotBlank(message = "Precisa ser preenchido") String nome, @NotBlank(message = "Precisa ser preenchido") String endereco, @NotNull(message = "Precisa ser preenchido") @Positive(message = "Não pode preencher com valor negativo") BigDecimal salario) {
        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
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

    //Só podemos usar o toModel após passar pelo validador de documento existente
    public Proposta toModel() {
        return new Proposta(documento, email, nome, endereco, salario);
    }


}
