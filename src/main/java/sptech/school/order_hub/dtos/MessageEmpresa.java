package sptech.school.order_hub.dtos;

import sptech.school.order_hub.entitiy.Empresa;

public record MessageEmpresa(
        Empresa empresa,
        String mensagem
) {
}