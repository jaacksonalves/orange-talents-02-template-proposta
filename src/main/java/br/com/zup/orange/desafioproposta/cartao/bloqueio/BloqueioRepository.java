package br.com.zup.orange.desafioproposta.cartao.bloqueio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloqueioRepository extends JpaRepository<Bloqueio, Long> {

    boolean existsByCartao_IdAndBloqueioAtivoIsTrue(Long idCartao);
}
