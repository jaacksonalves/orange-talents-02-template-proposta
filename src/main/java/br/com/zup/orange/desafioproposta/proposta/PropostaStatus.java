package br.com.zup.orange.desafioproposta.proposta;

import br.com.zup.orange.desafioproposta.proposta.analise.AnaliseStatus;

public enum PropostaStatus {
    NAO_ELEGIVEL, ELEGIVEL;

    public static PropostaStatus analiseToProposta(AnaliseStatus statusResultado) {
        if (statusResultado == AnaliseStatus.COM_RESTRICAO) {
            return NAO_ELEGIVEL;
        } else {
            return ELEGIVEL;
        }
    }



}
