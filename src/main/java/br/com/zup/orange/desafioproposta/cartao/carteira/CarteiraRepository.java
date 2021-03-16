package br.com.zup.orange.desafioproposta.cartao.carteira;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    boolean existsByEmissorCarteira(String emissor);
}

