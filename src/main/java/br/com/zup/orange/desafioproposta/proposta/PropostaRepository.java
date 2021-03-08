package br.com.zup.orange.desafioproposta.proposta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    boolean existsByDocumento(String documento);

    List<Proposta> findByStatusAndIdCartaoOrIdCartao(PropostaStatus elegivel, Object o, String s);

}
