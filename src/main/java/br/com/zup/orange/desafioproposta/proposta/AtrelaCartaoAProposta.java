package br.com.zup.orange.desafioproposta.proposta;

import br.com.zup.orange.desafioproposta.cartao.Cartao;
import br.com.zup.orange.desafioproposta.cartao.CartaoRepository;
import br.com.zup.orange.desafioproposta.cartao.ConectorCartaoResponse;
import br.com.zup.orange.desafioproposta.cartao.ConectorCartao;
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
    @Autowired
    private CartaoRepository cartaoRepository;

    //Verifica se propostas elegíveis já tem cartão cadastrado, caso não tenha, cadastra um cartão para ela.
    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void criaCartao() {
        List<Proposta> listaPropostaSemCartao = propostaRepository.findFirst10ByStatusAndCartaoIsNull(PropostaStatus.ELEGIVEL);

        listaPropostaSemCartao.forEach(propostas -> {
            Proposta proposta = listaPropostaSemCartao.get(0);
            ConectorCartaoResponse cartaoResposta = conectorCartao.apiToCartaoResposta(new AnalisePropostaRequest(proposta));
            Cartao cartao = cartaoResposta.toModel(proposta);
            cartaoRepository.save(cartao);
            proposta.updateCartao(cartao);
            propostaRepository.save(proposta);
        });
    }


}
