package br.com.zup.orange.desafioproposta.proposta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    boolean existsByDocumento(String documento);

    //Consulta os 10 primeiros resultados, de propostas elegiveis e sem cartão cadastrado
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Proposta> findFirst10ByStatusAndCartaoIsNull(PropostaStatus elegivel);

    Proposta findByEmail(String email);

}
