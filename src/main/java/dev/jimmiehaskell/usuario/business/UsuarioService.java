package dev.jimmiehaskell.usuario.business;

import dev.jimmiehaskell.usuario.business.converter.UsuarioConverter;
import dev.jimmiehaskell.usuario.business.dto.UsuarioDTO;
import dev.jimmiehaskell.usuario.infrastructure.entity.Usuario;
import dev.jimmiehaskell.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioConverter usuarioConverter;
    private final UsuarioRepository usuarioRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.fromUsuario(usuarioDTO);
        return  usuarioConverter.fromUsuarioDTO(usuarioRepository.save(usuario));
    }

}
