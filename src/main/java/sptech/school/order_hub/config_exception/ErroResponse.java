package sptech.school.order_hub.config_exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public record ErroResponse(
        String mensagem,
        LocalDateTime timestamp,
        int status
) {
    public static ErroResponse fromException(String message, Throwable ex, int status) {
        return new ErroResponse(
                message,
                LocalDateTime.now(),
                status
        );
    }
}
