package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.controller.empresa.request.BuscarEmpresaRequestDTO;
import sptech.school.order_hub.controller.empresa.request.CadastroEmpresaRequestDTO;
import sptech.school.order_hub.controller.empresa.response.BuscarEmpresaResponseDTO;
import sptech.school.order_hub.controller.empresa.response.CadastroEmpresaResponseDTO;
import sptech.school.order_hub.dtos.EmpresaDTO;
import sptech.school.order_hub.entitiy.Categoria;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.EnderecoRepository;
import sptech.school.order_hub.repository.UsuarioRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaServices {

    @Autowired
    EmpresaRepository repository;

    @Autowired
    EnderecoService enderecoService;

    @Autowired
    UsuarioRepository usuarioRepository;


    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private CategoriaServices categoriaServices;

    public Empresa updateById(Integer idEmpresa, Empresa empresaParaAtualizar) {

        Empresa empresaExistente = this.repository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        empresaParaAtualizar.setIdEmpresa(idEmpresa);
        empresaExistente.setNomeEmpresa(empresaParaAtualizar.getNomeEmpresa());
        empresaExistente.setEmailEmpresa(empresaParaAtualizar.getEmailEmpresa());
        empresaExistente.setCnpj(empresaParaAtualizar.getCnpj());
        empresaExistente.setTelefone(empresaParaAtualizar.getTelefone());

        return this.repository.save(empresaExistente);
    }

    public void deleteById(Integer idEmpresa) {
    }

    public Empresa findById(Integer idEmpresa) {
        return this.repository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));
    }


    public CadastroEmpresaResponseDTO create(Empresa empresa, Integer idPessoa) {

        Usuario usuario = usuarioRepository.findById(idPessoa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        if (empresaRepository.existsByCnpj(empresa.getCnpj())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CNPJ já cadastrado.");
        }

        Categoria categoria = categoriaServices.findByNome(empresa.getCategoria().getNome());

        Endereco endereco = enderecoService.create(empresa.getEndereco());

        Empresa empresaCriada = new Empresa();
        empresaCriada.setNomeEmpresa(empresa.getNomeEmpresa());
        empresaCriada.setEmailEmpresa(empresa.getEmailEmpresa());
        empresaCriada.setCnpj(empresa.getCnpj());
        empresaCriada.setTelefone(empresa.getTelefone());
        empresaCriada.setEndereco(endereco);
        empresaCriada.addUsuario(usuario);
        empresaCriada.setCategoria(categoria);
        usuario.setEmpresa(empresaCriada);
        empresaRepository.save(empresaCriada);
        usuarioRepository.save(usuario);


        categoria.addEmpresa(empresaCriada);

        categoriaServices.save(categoria);


        return CadastroEmpresaResponseDTO.from(empresaCriada);
    }

    public List<BuscarEmpresaResponseDTO> listarEmpresaPeloNome(BuscarEmpresaRequestDTO input) {

        List<Empresa> empresas = repository.findByNomeEmpresaOrServico(input.termo(), input.termo());

        if (empresas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma empresa encontrada.");
        }

        return empresas.stream()
                .map(empresa -> BuscarEmpresaResponseDTO.from(
                        empresa.getIdEmpresa(),
                        empresa.getNomeEmpresa(),
                        empresa.getEndereco(),
                        empresa.getIdImagem(),
                        empresa.getServicos())
                )
                .collect(Collectors.toList());
    }
}
