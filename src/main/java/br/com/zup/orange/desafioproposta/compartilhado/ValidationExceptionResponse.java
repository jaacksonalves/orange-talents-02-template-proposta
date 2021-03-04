package br.com.zup.orange.desafioproposta.compartilhado;

import java.time.LocalDateTime;

public class ValidationExceptionResponse {

    private String titulo;
    private String detalhe;
    private String status;
    private LocalDateTime time;

    public ValidationExceptionResponse(String titulo, String detalhe, String status, LocalDateTime time) {
        this.titulo = titulo;
        this.detalhe = detalhe;
        this.status = status;
        this.time = time;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
