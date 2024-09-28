package sptech.school.order_hub.controller.empresa.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BuscarEmpresaRequestDTO(
        @NotBlank
        String termo
) {

    public static String input(String termo){

        if(termo == null || termo.isBlank()){
            throw new IllegalArgumentException("Nome da empresa não pode ser vazio");
        }

        return String.valueOf(BuscarEmpresaRequestDTO.builder()
                .termo(termo)
                .build());
    }
}
