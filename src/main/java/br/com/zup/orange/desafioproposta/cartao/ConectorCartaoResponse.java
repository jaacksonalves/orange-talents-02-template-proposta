package br.com.zup.orange.desafioproposta.cartao;

import br.com.zup.orange.desafioproposta.cartao.integrados.*;
import br.com.zup.orange.desafioproposta.proposta.Proposta;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ConectorCartaoResponse {

    @JsonProperty("id")
    private String numeroCartao;
    private LocalDateTime emitidoEm;
    private String titular;
    private List<BloqueioRequest> bloqueios;
    private BigDecimal limite;
    private Long idProposta;

    public ConectorCartaoResponse(String numeroCartao, LocalDateTime emitidoEm, String titular, List<BloqueioRequest> bloqueios, BigDecimal limite, Long idProposta) {
        this.numeroCartao = numeroCartao;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.limite = limite;
        this.idProposta = idProposta;
    }

    public Cartao toModel(Proposta proposta) {
        return new Cartao(numeroCartao, emitidoEm, titular, bloqueios.stream().map(BloqueioRequest::montaBloqueio).collect(Collectors.toList()), limite, proposta);

    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public List<BloqueioRequest> getBloqueios() {
        return bloqueios;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public Long getIdProposta() {
        return idProposta;
    }
}
