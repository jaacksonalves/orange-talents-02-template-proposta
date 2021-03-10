package br.com.zup.orange.desafioproposta.proposta;

import br.com.zup.orange.desafioproposta.proposta.cartao.CartaoResposta;
import br.com.zup.orange.desafioproposta.proposta.cartao.ConectorCartao;
import br.com.zup.orange.desafioproposta.proposta.analise.AnalisePropostaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AtrelaCartaoAProposta {

    @Autowired
    private ConectorCartao conectorCartao;
    @Autowired
    private PropostaRepository propostaRepository;

    //Verifica se propostas elegíveis já tem cartão cadastrado, caso não tenha, cadastra um cartão para ela.
    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void criaCartao() {
        List<Proposta> listaPropostaSemCartao = propostaRepository.findFirst10ByStatusAndIdCartaoIsNullOrIdCartao(PropostaStatus.ELEGIVEL, "");

        if (!listaPropostaSemCartao.isEmpty()){
            listaPropostaSemCartao.forEach(propostas -> {
                Proposta proposta = listaPropostaSemCartao.get(0);
                CartaoResposta cartaoResposta = conectorCartao.cartaoResposta(new AnalisePropostaRequest(proposta));

                proposta.setIdCartao(cartaoResposta.getId());
                propostaRepository.save(proposta);
            });
        }


    }
}
