package sptech.school.order_hub.dtos;

import lombok.Builder;
import sptech.school.order_hub.entitiy.Servico;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Builder
public record ServicoAddDTO (
        int idServico,
        String nomeServico,
        Double valorServico,
        String descricao,
        String corReferenciaHex,
        String duracao
) {
    public static ServicoAddDTO from(Servico servico) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Trata valores nulos para duracao
        String duracaoComoString = servico.getDuracao() != null
                ? servico.getDuracao().format(formatter)
                : "00:00:00"; // Valor padrão

        return ServicoAddDTO.builder()
                .idServico(servico.getIdServico())
                .nomeServico(servico.getNomeServico())
                .valorServico(servico.getValorServico())
                .descricao(servico.getDescricao())
                .corReferenciaHex(servico.getCorReferenciaHex())
                .duracao(duracaoComoString)
                .build();
    }


    public static Servico toEntity(ServicoAddDTO servicoDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime duracaoComoLocalTime = null;
        if (servicoDto.duracao() != null && !servicoDto.duracao().isEmpty()) {
            try {
                duracaoComoLocalTime = LocalTime.parse(servicoDto.duracao(), formatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Formato de tempo inválido: " + servicoDto.duracao());
            }
        }

        return Servico.builder()
                .nomeServico(servicoDto.nomeServico())
                .valorServico(servicoDto.valorServico())
                .descricao(servicoDto.descricao())
                .corReferenciaHex(servicoDto.corReferenciaHex())
                .duracao(duracaoComoLocalTime)
                .build();
    }

}
