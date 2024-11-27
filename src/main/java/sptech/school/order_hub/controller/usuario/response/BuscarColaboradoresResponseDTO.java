package sptech.school.order_hub.controller.usuario.response;

import lombok.Builder;
import sptech.school.order_hub.dtos.AgendamentoDTO;
import sptech.school.order_hub.entitiy.Usuario;

import java.util.List;

@Builder
public record BuscarColaboradoresResponseDTO(
        Integer idFuncionario,
        String nomeFuncionario,
        String funcao,
        Integer idAgenda,
        List<AgendamentoDTO> agendamentoDTOS,
        String telefone
) {

    public static BuscarColaboradoresResponseDTO from(Usuario usuario, List<AgendamentoDTO> agendamentos) {
        return new BuscarColaboradoresResponseDTO(
                usuario.getIdPessoa(),
                usuario.getNomePessoa(),
                usuario.getFuncao() == null ? "Sem Função" : usuario.getFuncao().getNomeFuncao(),
                usuario.getAgenda().getIdAgenda(),
                agendamentos,
                usuario.getNumeroTelefone()
        );
    }
}
