package sptech.school.order_hub.controller.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.usuario.request.AuthRequestDTO;
import sptech.school.order_hub.controller.usuario.request.CadastroUsuarioRequestDTO;
import sptech.school.order_hub.controller.usuario.response.AuthResponseDTO;
import sptech.school.order_hub.controller.usuario.response.CadastroUsuarioResponseDTO;
import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.services.UsuarioServices;

import javax.naming.AuthenticationException;
import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServices services;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            return services.autenticar(authRequestDTO.toEntity());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping()
    public ResponseEntity<CadastroUsuarioResponseDTO> create(@RequestBody CadastroUsuarioRequestDTO usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.services.create(usuario.toEntity()));
    }

    @PostMapping("/endereco/{idUsuario}")
    public ResponseEntity<Usuario> createEndereco(@RequestBody Endereco endereco, @PathVariable Integer idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(this.services.createEndereco(endereco, idUsuario));
    }

    @PostMapping("/empresa/{idEmpresa}")
    public ResponseEntity<Usuario> createWithEmpresa(@RequestBody Usuario usuario, @PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(this.services.createWithEmpresa(usuario, idEmpresa));
    }

    @GetMapping("empresa/{idEmpresa}")
    public ResponseEntity<List<Usuario>> finfAll(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(this.services.findAllByEmpresa(idEmpresa));
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(this.services.findById(idUsuario));
    }

    @PutMapping("{id}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizar) {
        return ResponseEntity.status(HttpStatus.OK).body(this.services.update(id, usuarioAtualizar));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.services.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
