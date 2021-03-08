package br.com.zup.orange.desafioproposta.proposta;

import br.com.zup.orange.desafioproposta.cartao.CartaoResposta;
import br.com.zup.orange.desafioproposta.cartao.ConectorCartao;
import br.com.zup.orange.desafioproposta.proposta.analise.AnalisePropostaRequest;
import br.com.zup.orange.desafioproposta.proposta.analise.AnaliseResponse;
import br.com.zup.orange.desafioproposta.proposta.analise.ConectorAnaliseProposta;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private ConectorAnaliseProposta conectorAnaliseProposta;
    @Autowired
    private ConectorCartao conectorCartao;


    @Transactional
    @PostMapping
    public ResponseEntity<?> criaProposta(@Valid @RequestBody NovaPropostaRequest request, UriComponentsBuilder uriComponentsBuilder) {
        //verifica se o documento está cadastrado ou não no banco de dados, pois não pode haver mais de uma proposta pro mesmo documento
        if (propostaRepository.existsByDocumento(request.getDocumento())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Já existe proposta cadastrada para esse documento!");
        }
        Proposta novaProposta = request.toModel();
        propostaRepository.save(novaProposta);
        URI uri = uriComponentsBuilder.path("/api/propostas/{id}").buildAndExpand(novaProposta.getId()).toUri();

        try {
            AnaliseResponse respostaAnalise = conectorAnaliseProposta.analiseStatus(new AnalisePropostaRequest(novaProposta));
            novaProposta.setStatus(PropostaStatus.analiseToProposta(respostaAnalise.getStatusResultado()));

            return ResponseEntity.created(uri).build();
        } catch (FeignException e) {
            novaProposta.setStatus(PropostaStatus.NAO_ELEGIVEL);

            return ResponseEntity.unprocessableEntity().location(uri).build();
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> consultaProposta(@PathVariable("id") Long idProposta) {
        Optional<Proposta> proposta = propostaRepository.findById(idProposta);
        if (proposta.isPresent()) {
            Proposta propostaEcontrada = propostaRepository.getOne(idProposta);
            return ResponseEntity.ok(new PropostaResponse(propostaEcontrada));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado");

    }


    //Verifica se propostas elegíveis já tem cartão cadastrado, caso não tenha, cadastra um cartão para ela.
    @Scheduled(fixedDelay = 5000)
    public void criaCartao() {
        List<Proposta> listaPropostaSemCartao = propostaRepository.findByStatusAndIdCartaoOrIdCartao(PropostaStatus.ELEGIVEL, null, "");

        listaPropostaSemCartao.forEach(propostas -> {
            Proposta proposta = listaPropostaSemCartao.get(0);
            CartaoResposta cartaoResposta = conectorCartao.cartaoResposta(new AnalisePropostaRequest(proposta));

            proposta.setIdCartao(cartaoResposta.getId());
            propostaRepository.save(proposta);
        });


//        if (!listaPropostaSemCartao.isEmpty()) {
//            CartaoResposta cartaoResposta = conectorCartao.cartaoResposta(listaPropostaSemCartao.get(0).toAnaliseRequest());
//            Proposta proposta = listaPropostaSemCartao.get(0);
//            proposta.setIdCartao(cartaoResposta.getId());
//            propostaRepository.save(proposta);
//        }

    }


}