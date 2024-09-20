package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.order_hub.dtos.EmpresaDTO;
import sptech.school.order_hub.entitiy.Empresa;
import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.repository.EmpresaRepository;
import sptech.school.order_hub.repository.EnderecoRepository;
import sptech.school.order_hub.repository.UsuarioRepository;

import java.util.List;

@Service
public class EmpresaServices {

    @Autowired
    EmpresaRepository repository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa updateById(Integer idEmpresa, Empresa empresaParaAtualizar) {

        Empresa empresaExistente = this.repository.findById(idEmpresa)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));

        empresaParaAtualizar.setIdEmpresa(idEmpresa);
        empresaExistente.setNomeEmpresa(empresaParaAtualizar.getNomeEmpresa());
        empresaExistente.setEmailEmpresa(empresaParaAtualizar.getEmailEmpresa());
        empresaExistente.setCnpj(empresaParaAtualizar.getCnpj());
        empresaExistente.setTelefone(empresaParaAtualizar.getTelefone());
        empresaExistente.setImagem(empresaParaAtualizar.getImagem());

        return this.repository.save(empresaExistente);
    }

    public void deleteById(Integer idEmpresa) {
    }

    public Empresa findById(Integer idEmpresa) {
        return null;
    }


    public Empresa create(EmpresaDTO empresaDTO) {
        Usuario usuario = usuarioRepository.findById(empresaDTO.idPessoa())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        Endereco endereco = createEndereco(empresaDTO);

        Empresa empresa = new Empresa();
        empresa.setNomeEmpresa(empresaDTO.nomeEmpresa());
        empresa.setEmailEmpresa(empresaDTO.emailEmpresa());
        empresa.setCnpj(empresaDTO.cnpj());
        empresa.setTelefone(empresaDTO.telefone());
        empresa.setEndereco(endereco);
        empresa.addUsuario(usuario);

        usuario.setEmpresa(empresa);

        empresaRepository.save(empresa);

        usuarioRepository.save(usuario);
        return empresa;
    }

    private Endereco createEndereco(EmpresaDTO empresaDTO) {
        Endereco endereco = new Endereco();
        endereco.setLogradouro(empresaDTO.endereco().logradouro());
        endereco.setBairro(empresaDTO.endereco().bairro());
        endereco.setCidade(empresaDTO.endereco().cidade());
        endereco.setEstado(empresaDTO.endereco().estado());
        endereco.setCep(empresaDTO.endereco().cep());
        endereco.setNumero(empresaDTO.endereco().numero());
        endereco.setComplemento(empresaDTO.endereco().complemento());
        return enderecoRepository.save(endereco);
    }

    public List<Empresa> listar() {
        return null;
    }
}
