package sptech.school.order_hub.dtos;

import sptech.school.order_hub.entitiy.Empresa;

public record PerfilEmpresaDTO(
        Empresa empresa,
        String nome
) {

        public static PerfilEmpresaDTO from(Empresa empresa, String nome) {
            return new PerfilEmpresaDTO(
                    empresa,
                    nome
            );
        }
}
