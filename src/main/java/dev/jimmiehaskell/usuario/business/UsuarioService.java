package dev.jimmiehaskell.usuario.business;

import dev.jimmiehaskell.usuario.business.converter.UsuarioConverter;
import dev.jimmiehaskell.usuario.business.dto.UsuarioDTO;
import dev.jimmiehaskell.usuario.infrastructure.entity.Usuario;
import dev.jimmiehaskell.usuario.infrastructure.exceptions.ConflictException;
import dev.jimmiehaskell.usuario.infrastructure.exceptions.ResourceNotFoundException;
import dev.jimmiehaskell.usuario.infrastructure.repository.UsuarioRepository;
import dev.jimmiehaskell.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        try {
            emailExiste(usuarioDTO.getEmail());
            usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            Usuario usuario = usuarioConverter.fromUsuario(usuarioDTO);
            return usuarioConverter.fromUsuarioDTO(usuarioRepository.save(usuario));
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado ", e.getCause());
        }
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new RuntimeException("Email já cadastrado: ", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
            () -> new ResourceNotFoundException("Email não encontrado " + email));
    }

    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO dto) {
        String email = jwtUtil.extractEmailToken(token.substring(7));
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
            () -> new ResourceNotFoundException("Email não localizado: " + email));
        Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
        return usuarioConverter.fromUsuarioDTO(usuarioRepository.save(usuario));
    }
}
