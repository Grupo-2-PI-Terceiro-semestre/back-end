package sptech.school.order_hub.controller.usuario;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.order_hub.controller.agendamento.request.BuscarAgendamentoRequestDTO;
import sptech.school.order_hub.controller.usuario.request.AuthRequestDTO;
import sptech.school.order_hub.controller.usuario.request.CadastroUsuarioRequestDTO;
import sptech.school.order_hub.controller.usuario.response.AuthResponseDTO;
import sptech.school.order_hub.controller.usuario.response.BuscarColaboradoresResponseDTO;
import sptech.school.order_hub.controller.usuario.response.CadastroUsuarioResponseDTO;
import sptech.school.order_hub.entitiy.Endereco;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.services.UsuarioServices;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Usuario", description = "Controller de usuários")
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServices services;


    @Operation(
            summary = "Realiza login e retorna token de autenticação",
            description = "Este endpoint autentica um usuário e retorna um token JWT para acesso.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciais de login",
                    content = @Content(
                            schema = @Schema(implementation = AuthRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de login",
                                    value = """
                                            {
                                              "emailPessoa": "felipe.silva@example.com",
                                              "senha": "senhaSecreta1"
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login bem-sucedido",
                            content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação nas credenciais",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Exemplo de erro 400",
                                            value = "\"Validation error: Email ou senha inválidos.\""
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Credenciais inválidas ou não autenticadas",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Exemplo de erro 401",
                                            value = "\"Erro de autenticação: Usuário ou senha incorretos.\""
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acesso negado (forbidden)",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Exemplo de erro 403",
                                            value = "\"Forbidden: Acesso não autorizado.\""
                                    )
                            )
                    ),
            }
    )
    @Tag(name = "Autenticação", description = "Autenticação de usuários")
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO) {
        try {
            return services.autenticar(authRequestDTO.toEntity());
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
    }


    @Operation(summary = "Cadastrar um usuário", description = "Cadastra um usuário")
    @PostMapping()
    public ResponseEntity<CadastroUsuarioResponseDTO> create(@RequestBody CadastroUsuarioRequestDTO usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.services.create(usuario.toEntity()));
    }

    @Operation(summary = "Cadastrar um endereço para um usuário", description = "Cadastra um endereço para um usuário")
    @PostMapping("/endereco/{idUsuario}")
    public ResponseEntity<Usuario> createEndereco(@RequestBody Endereco endereco, @PathVariable Integer idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(this.services.createEndereco(endereco, idUsuario));
    }

    @Operation(summary = "Cadastrar um usuário com uma empresa", description = "Cadastra um usuário com uma empresa")
    @PostMapping("/empresa/{idEmpresa}")
    public ResponseEntity<Usuario> createWithEmpresa(@RequestBody Usuario usuario, @PathVariable Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.OK).body(this.services.createWithEmpresa(usuario, idEmpresa));
    }

    @Operation(summary = "Buscar todos os usuários", description = "Busca todos os usuários")
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<BuscarColaboradoresResponseDTO>> finfAll(@PathVariable Integer idEmpresa, @RequestParam LocalDate dataAgendamento) {
        var request = BuscarAgendamentoRequestDTO.of(dataAgendamento);
        return ResponseEntity.status(HttpStatus.OK).body(this.services.findAllByColaboradores(idEmpresa, request, false));
    }


    @Operation(summary = "Exportar agendamentos", description = "Exporta os agendamentos de uma empresa")
    @GetMapping("/agendamentos/exportar/{idEmpresa}")
    public void exportarAgendamentos(@PathVariable Integer idEmpresa, @RequestParam LocalDate dataAgendamento, HttpServletResponse response) {
        try {

            services.exportarAgendamentos(BuscarAgendamentoRequestDTO.of(dataAgendamento), idEmpresa, response);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Buscar um usuário por id", description = "Busca um usuário")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer idUsuario) {
        return ResponseEntity.status(HttpStatus.OK).body(this.services.findById(idUsuario));
    }

    @Operation(summary = "Atualizar um usuário", description = "Atualiza um usuário")
    @PutMapping("{id}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizar) {
        return ResponseEntity.status(HttpStatus.OK).body(this.services.update(id, usuarioAtualizar));
    }

    @Operation(summary = "Deletar um usuário", description = "Deleta um usuário")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.services.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
