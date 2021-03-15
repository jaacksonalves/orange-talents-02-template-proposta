package br.com.zup.orange.desafioproposta.cartao.bloqueio;

public class BloqueioCartaoResponse {

    private String resultado;

    public BloqueioCartaoResponse(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public BloqueioCartaoResponse() {
    }
}
