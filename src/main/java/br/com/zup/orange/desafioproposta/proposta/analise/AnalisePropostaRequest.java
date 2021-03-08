package br.com.zup.orange.desafioproposta.proposta.analise;

import br.com.zup.orange.desafioproposta.proposta.Proposta;

public class AnalisePropostaRequest {

    private final String documento;
    private final String nome;
    private final Long idProposta;


    public AnalisePropostaRequest(Proposta novaProposta) {
        this.documento = novaProposta.getDocumento();
        this.nome = novaProposta.getNome();
        this.idProposta = novaProposta.getId();
    }

    public Long getIdProposta() {
        return idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }
}
