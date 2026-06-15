package dev.jimmiehaskell.usuario.business;

import dev.jimmiehaskell.usuario.infrastructure.clients.ViaCepClient;
import dev.jimmiehaskell.usuario.infrastructure.clients.ViaCepDTO;
import dev.jimmiehaskell.usuario.infrastructure.exceptions.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {
    private final ViaCepClient viaCepClient;

    public ViaCepDTO buscaDadosEndereco(String cep) {
        return viaCepClient.buscaDadosEndereco(processarCep(cep));
    }

    private String processarCep(String cep) {
        String cepFormatado = cep
            .replace(" ", "")
            .replace("-", "")
            .replace(".", "");

        if (!cepFormatado.matches("\\d+") || !Objects.equals(cepFormatado.length(), 8)) {
            throw new IllegalArgumentException("O CEP contém caracteres inválidos, favor verificar");
        }

        return cepFormatado;
    }
}
