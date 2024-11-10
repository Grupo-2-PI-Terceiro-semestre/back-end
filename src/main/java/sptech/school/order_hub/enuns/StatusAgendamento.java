package sptech.school.order_hub.enuns;

public enum StatusAgendamento {
    AGENDADO,
    CANCELADO,
    REALIZADO;

    public static StatusAgendamento fromString(String status) {
        return switch (status) {
            case "AGENDADO" -> AGENDADO;
            case "CANCELADO" -> CANCELADO;
            case "REALIZADO" -> REALIZADO;
            default -> throw new IllegalArgumentException("Status inválido: " + status);
        };
    }
}


