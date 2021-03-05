package br.com.zup.orange.desafioproposta.proposta.analise;

public class AnaliseRequest {

    private final String documento;
    private final String nome;
    private final Long idProposta;

    public AnaliseRequest(Long idProposta, String documento, String nome) {
        this.idProposta = idProposta;
        this.documento = documento;
        this.nome = nome;
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
