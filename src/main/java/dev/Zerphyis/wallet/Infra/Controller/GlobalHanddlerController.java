package dev.Zerphyis.wallet.Infra.Controller;

import dev.Zerphyis.wallet.Application.Dto.ErroResponseDto;
import dev.Zerphyis.wallet.Application.Exceptions.ClienteNaoEncontradoException;
import dev.Zerphyis.wallet.Application.Exceptions.ClienteNotFound;
import dev.Zerphyis.wallet.Application.Exceptions.DuplicatraException;
import dev.Zerphyis.wallet.Application.Exceptions.SaldoInsuficienteException;
import dev.Zerphyis.wallet.Application.Exceptions.ValorInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalHanddlerController {

        @ExceptionHandler({ClienteNaoEncontradoException.class, ClienteNotFound.class})
        public ResponseEntity<ErroResponseDto> handleClienteNaoEncontrado(RuntimeException ex) {
            ErroResponseDto error = new ErroResponseDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(DuplicatraException.class)
        public ResponseEntity<ErroResponseDto> handleDuplicatra(DuplicatraException ex) {
            ErroResponseDto error = new ErroResponseDto(
                    HttpStatus.CONFLICT.value(),
                    ex.getMessage() != null ? ex.getMessage() : "Registro ou transação duplicada."
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }

        @ExceptionHandler(SaldoInsuficienteException.class)
        public ResponseEntity<ErroResponseDto> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
            ErroResponseDto error = new ErroResponseDto(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
        }

        @ExceptionHandler(ValorInvalidoException.class)
        public ResponseEntity<ErroResponseDto> handleValorInvalido(ValorInvalidoException ex) {
            String mensagem = ex.getMessage() != null ? ex.getMessage() : "Valor informado é inválido.";
            ErroResponseDto error = new ErroResponseDto(HttpStatus.BAD_REQUEST.value(), mensagem);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErroResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
            List<String> erros = ex.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .toList();

            ErroResponseDto error = new ErroResponseDto(
                    HttpStatus.BAD_REQUEST.value(),
                    "Falha na validação dos campos de entrada.",
                    erros
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErroResponseDto> handleGenericException(Exception ex) {
            ErroResponseDto error = new ErroResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Ocorreu um erro interno inesperado no servidor."
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

