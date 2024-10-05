package sptech.school.order_hub.dtos;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Servico;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

@Builder
public record ServicoDTO(
        int idServico,
        String nomeServico,
        String descricao,
        String corReferenciaHex,
        long duracao // Agora é long em milissegundos
) {

    public static ServicoDTO fromEntity(Servico servico) {

        long duracaoEmMilissegundos = Duration.between(LocalTime.MIDNIGHT, servico.getDuracao()).toMillis();

        return ServicoDTO.builder()
                .idServico(servico.getIdServico())
                .nomeServico(servico.getNomeServico())
                .descricao(servico.getDescricao())
                .corReferenciaHex(servico.getCorReferenciaHex())
                .duracao(duracaoEmMilissegundos) // Converte para milissegundos
                .build();
    }
}
