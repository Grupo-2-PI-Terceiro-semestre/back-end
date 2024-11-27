package sptech.school.order_hub.config.security.exeption;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now().toString()); // Adiciona a data e hora do erro
        errorDetails.put("status", HttpServletResponse.SC_FORBIDDEN); // Código de status HTTP
        errorDetails.put("error", "Access Denied"); // Tipo de erro
        errorDetails.put("message", ex.getMessage()); // Mensagem detalhada do erro
        errorDetails.put("path", request.getRequestURI()); // URI do recurso que causou o erro
        errorDetails.put("method", request.getMethod()); // Método HTTP usado na requisição

        String jsonResponse = new ObjectMapper().writeValueAsString(errorDetails);

        response.getWriter().write(jsonResponse);
    }
}

