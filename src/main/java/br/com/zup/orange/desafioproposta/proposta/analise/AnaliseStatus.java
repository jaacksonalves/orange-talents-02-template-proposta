package br.com.zup.orange.desafioproposta.proposta.analise;

import br.com.zup.orange.desafioproposta.proposta.PropostaStatus;

public enum AnaliseStatus {
    COM_RESTRICAO(PropostaStatus.NAO_ELEGIVEL),
    SEM_RESTRICAO(PropostaStatus.ELEGIVEL);

    private final PropostaStatus propostaStatus;

    AnaliseStatus(PropostaStatus propostaStatus) {
        this.propostaStatus = propostaStatus;
    }

    public PropostaStatus getPropostaStatus() {
        return propostaStatus;
    }
}
