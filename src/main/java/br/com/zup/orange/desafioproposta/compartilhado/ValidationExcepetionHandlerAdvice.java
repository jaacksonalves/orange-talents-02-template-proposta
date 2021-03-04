package br.com.zup.orange.desafioproposta.compartilhado;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExcepetionHandlerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponse> handler(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        String titulos = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String detalhes = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        String status = String.valueOf(HttpStatus.BAD_REQUEST);
        LocalDateTime time = LocalDateTime.now(ZoneId.of("GMT-3"));

        ValidationExceptionResponse validationExceptionResponse = new ValidationExceptionResponse(titulos, detalhes, status, time);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationExceptionResponse);

    }
}
