package sptech.school.order_hub.controller.empresa.request;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CNPJ;
import sptech.school.order_hub.dtos.CategoriaDTO;
import sptech.school.order_hub.dtos.EnderecoDTO;
import sptech.school.order_hub.entitiy.Empresa;

public record CadastroEmpresaRequestDTO(
        String nomeEmpresa,
        @Email
        String emailEmpresa,
        @CNPJ
        String cnpj,
        String telefone,
        EnderecoDTO endereco,
        CategoriaDTO categoria,
        Integer idPessoa
) {

        public Empresa toEntity(){
                return Empresa.builder()
                        .nomeEmpresa(nomeEmpresa)
                        .emailEmpresa(emailEmpresa)
                        .cnpj(cnpj)
                        .telefone(telefone)
                        .endereco(endereco.toEntity())
                        .categoria(categoria.toEntity())
                        .build();
        }
}
