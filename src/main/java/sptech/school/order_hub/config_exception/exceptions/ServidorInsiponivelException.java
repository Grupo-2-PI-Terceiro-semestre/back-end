package sptech.school.order_hub.config_exception.exceptions;

public class ServidorInsiponivelException extends RuntimeException {
    public ServidorInsiponivelException(String message) {
        super(message);
    }

    public ServidorInsiponivelException(String message, Throwable cause) {
        super(message, cause);
    }
}
