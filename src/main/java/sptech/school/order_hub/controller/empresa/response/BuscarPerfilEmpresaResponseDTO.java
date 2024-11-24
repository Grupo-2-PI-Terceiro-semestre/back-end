package sptech.school.order_hub.controller.empresa.response;

import sptech.school.order_hub.dtos.EnderecoDTO;
import sptech.school.order_hub.entitiy.Imagem;
import sptech.school.order_hub.entitiy.Servico;
import sptech.school.order_hub.entitiy.Usuario;

import java.time.LocalTime;
import java.util.List;

public record BuscarPerfilEmpresaResponseDTO(
        String nomeEmpresa,
        EnderecoDTO endereco,
        List<ImagemSimplificadaDTO> imagems,
        List<ServicoSimplificadoDTO> servicos,
        List<UsuarioSimplificadoDTO> usuario
) {

    public record UsuarioSimplificadoDTO(
            int idUsuario,
            String nomePessoa
    ) {
        public static UsuarioSimplificadoDTO from(Usuario usuario) {
            return new UsuarioSimplificadoDTO(
                    usuario.getIdPessoa(),
                    usuario.getNomePessoa()
            );
        }
    }

    public record ServicoSimplificadoDTO(
            int idServico,
            String nomeServico,
            LocalTime duracaoServico,
            String descricaoServico,
            double precoServico
    ) {
        public static ServicoSimplificadoDTO from(Servico servico) {
            return new ServicoSimplificadoDTO(
                    servico.getIdServico(),
                    servico.getNomeServico(),
                    servico.getDuracao(),
                    servico.getDescricao(),
                    servico.getValorServico()
            );
        }
    }

    public record ImagemSimplificadaDTO(
            String urlImagem
    ) {
        public static ImagemSimplificadaDTO from(Imagem imagem) {
            return new ImagemSimplificadaDTO(
                    imagem.getUrlImagem()
            );
        }
    }

}
