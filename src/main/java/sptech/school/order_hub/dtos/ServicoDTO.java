package sptech.school.order_hub.dtos;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Servico;

import java.time.Duration;
import java.time.LocalTime;

@Builder
public record ServicoDTO(
        int idServico,
        String nomeServico,
        Double valorServico,
        String descricao,
        String corReferenciaHex,
        long duracao // Agora é long em milissegundos
) {

    public static ServicoDTO from(Servico servico) {
        long duracaoEmMilissegundos = Duration.between(LocalTime.MIDNIGHT, servico.getDuracao()).toMillis();

        return ServicoDTO.builder()
                .idServico(servico.getIdServico())
                .nomeServico(servico.getNomeServico())
                .valorServico(servico.getValorServico())
                .descricao(servico.getDescricao())
                .corReferenciaHex(servico.getCorReferenciaHex())
                .duracao(duracaoEmMilissegundos)
                .build();
    }

    public static Servico toEntity(ServicoDTO servicoDto) {

        LocalTime duracaoComoLocalTime = LocalTime.MIDNIGHT.plus(Duration.ofMillis(servicoDto.duracao()));

        return Servico.builder()
                .nomeServico(servicoDto.nomeServico())
                .valorServico(servicoDto.valorServico())
                .descricao(servicoDto.descricao())
                .corReferenciaHex(servicoDto.corReferenciaHex())
                .duracao(duracaoComoLocalTime) // Já está no formato LocalTime
                .build();
    }
}
