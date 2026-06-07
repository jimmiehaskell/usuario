package dev.jimmiehaskell.usuario.business.converter;

import dev.jimmiehaskell.usuario.business.dto.EnderecoDTO;
import dev.jimmiehaskell.usuario.business.dto.TelefoneDTO;
import dev.jimmiehaskell.usuario.business.dto.UsuarioDTO;
import dev.jimmiehaskell.usuario.infrastructure.entity.Endereco;
import dev.jimmiehaskell.usuario.infrastructure.entity.Telefone;
import dev.jimmiehaskell.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario fromUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
            .nome(usuarioDTO.getNome())
            .email(usuarioDTO.getEmail())
            .senha(usuarioDTO.getSenha())
            .enderecos(fromListaEnderecos(usuarioDTO.getEnderecos()))
            .telefones(fromListaTelefones(usuarioDTO.getTelefones()))
            .build();
    }

    public List<Endereco> fromListaEnderecos(List<EnderecoDTO> enderecosDTO) {
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTO enderecoDTO : enderecosDTO) {
            enderecos.add(fromEndereco(enderecoDTO));
        }
        return enderecos;
    }

    public Endereco fromEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
            .rua(enderecoDTO.getRua())
            .numero(enderecoDTO.getNumero())
            .cidade(enderecoDTO.getCidade())
            .complemento(enderecoDTO.getComplemento())
            .cep(enderecoDTO.getCep())
            .estado(enderecoDTO.getEstado())
            .build();
    }

    public List<Telefone> fromListaTelefones(List<TelefoneDTO> telefonesDTO) {
        return telefonesDTO.stream().map(this::fromTelefone).toList();
    }

    public Telefone fromTelefone(TelefoneDTO telefoneDTO) {
        return Telefone.builder()
            .numero(telefoneDTO.getNumero())
            .ddd(telefoneDTO.getDdd())
            .build();
    }

    //

    public UsuarioDTO fromUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
            .nome(usuario.getNome())
            .email(usuario.getEmail())
            .senha(usuario.getSenha())
            .enderecos(fromListaEnderecosDTO(usuario.getEnderecos()))
            .telefones(fromListaTelefonesDTO(usuario.getTelefones()))
            .build();
    }

    public List<EnderecoDTO> fromListaEnderecosDTO(List<Endereco> enderecos) {
        List<EnderecoDTO> enderecosDTO = new ArrayList<>();
        for (Endereco endereco : enderecos) {
            enderecosDTO.add(fromEnderecoDTO(endereco));
        }
        return enderecosDTO;
    }

    public EnderecoDTO fromEnderecoDTO(Endereco endereco) {
        return EnderecoDTO.builder()
            .rua(endereco.getRua())
            .numero(endereco.getNumero())
            .cidade(endereco.getCidade())
            .complemento(endereco.getComplemento())
            .cep(endereco.getCep())
            .estado(endereco.getEstado())
            .build();
    }

    public List<TelefoneDTO> fromListaTelefonesDTO(List<Telefone> telefones) {
        return telefones.stream().map(this::fromTelefoneDTO).toList();
    }

    public TelefoneDTO fromTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
            .numero(telefone.getNumero())
            .ddd(telefone.getDdd())
            .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entiry) {
        return Usuario.builder()
            .id(entiry.getId())
            .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entiry.getNome())
            .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entiry.getSenha())
            .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entiry.getEmail())
            .enderecos(entiry.getEnderecos())
            .telefones(entiry.getTelefones())
            .build();
    }

}
