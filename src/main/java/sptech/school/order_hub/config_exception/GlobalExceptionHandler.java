package sptech.school.order_hub.config_exception;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.config_exception.exceptions.ConflitoAoCadastrarRecursoException;
import sptech.school.order_hub.config_exception.exceptions.RecursoNaoEncontradoException;
import sptech.school.order_hub.config_exception.exceptions.SemConteudoException;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: " + errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: " + ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Erro de autenticação: " + e.getMessage());
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErroResponse> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErroResponse.fromException(ex.getMessage(), ex, HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(SemConteudoException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ErroResponse> handleSemConteudoException(SemConteudoException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ErroResponse.fromException(ex.getMessage(), ex, HttpStatus.NO_CONTENT.value()));
    }

    @ExceptionHandler(ConflitoAoCadastrarRecursoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErroResponse> handleConflitoAoCadastrarRecursoException(ConflitoAoCadastrarRecursoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErroResponse.fromException(ex.getMessage(), ex, HttpStatus.CONFLICT.value()));
    }
}
