package sptech.school.order_hub.config_exception.exceptions;

public class ParametrosInvalidosException extends RuntimeException {
    public ParametrosInvalidosException(String message) {
        super(message);
    }

    public ParametrosInvalidosException(String message, Throwable cause) {
        super(message, cause);
    }
}
