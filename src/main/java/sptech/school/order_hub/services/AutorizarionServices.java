package sptech.school.order_hub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sptech.school.order_hub.entitiy.Usuario;
import sptech.school.order_hub.repository.UsuarioRepository;

@Service
public class AutorizarionServices implements UserDetailsService {


    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = (Usuario) usuarioRepository.findByEmailPessoa(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        return usuario;
    }


}
