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
    private final String documento;
    @NotBlank(message = "Precisa ser preenchido")
    @Email(message = "Deve preencher com email válido")
    private final String email;
    @NotBlank(message = "Precisa ser preenchido")
    private final String nome;
    @NotBlank(message = "Precisa ser preenchido")
    private final String endereco;
    @NotNull(message = "Precisa ser preenchido")
    @Positive(message = "Não pode preencher com valor negativo")
    private final BigDecimal salario;

    public NovaPropostaRequest(@NotBlank String documento, @NotBlank @Email String email, @NotBlank String nome, @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
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
    public Proposta toModel(EntityManager em) {
        Assert.isTrue(this.validaDocumentoNaoCadastrado(em), "Documento já está cadastrado");
        return new Proposta(documento, email, nome, endereco, salario);
    }

    //Verifica se o documento já está cadastrado no sistema, pois não podemos ter duas propostas pro mesmo documento
    public boolean validaDocumentoNaoCadastrado(EntityManager em) {
        List<?> listaPropostasPorDocumento = em.createQuery("select p from Proposta p where p.documento=:documento")
                .setParameter("documento", this.documento).getResultList();

        return listaPropostasPorDocumento.isEmpty();
    }
}
