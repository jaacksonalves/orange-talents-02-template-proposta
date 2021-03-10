package br.com.zup.orange.desafioproposta.proposta.analise;

public class AnalisePropostaResponse {


    private String documento;
    private String nome;
    private AnaliseStatus statusResultado;
    private Long idProposta;

    public AnalisePropostaResponse(String documento, String nome, AnaliseStatus statusResultado, Long idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.statusResultado = statusResultado;
        this.idProposta = idProposta;
    }

    public AnaliseStatus getStatusResultado() {
        return statusResultado;
    }

}
