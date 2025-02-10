package sptech.school.order_hub.config_exception.exceptions;

public class ConflitoAoCadastrarRecursoException extends RuntimeException {
    public ConflitoAoCadastrarRecursoException(String message) {
        super(message);
    }

    public ConflitoAoCadastrarRecursoException(String message, Throwable cause) {
        super(message, cause);
    }
}
