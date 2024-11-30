package sptech.school.order_hub.enuns;

public enum StatusAtividade {
    ATIVO,
    INATIVO;

    public static StatusAtividade fromString(String status) {
        return switch (status) {
            case "ATIVO" -> ATIVO;
            case "INATIVO" -> INATIVO;
            default -> throw new IllegalArgumentException("Status inválido: " + status);
        };
    }
}
