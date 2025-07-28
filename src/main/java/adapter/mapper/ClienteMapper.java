package adapter.mapper;

import adapter.controller.request.ClienteRequestDto;
import adapter.controller.request.ClienteRequestDto.EnderecoRequestDto;
import adapter.controller.response.ClienteResponseDto;
import adapter.controller.response.ClienteResponseDto.EnderecoResponseDto;
import core.domain.entities.cliente.Cliente;
import core.domain.entities.endereco.Endereco;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteMapper {

    public Cliente toClienteDomain(ClienteRequestDto dto) {
        if (dto == null) return null;
        Endereco endereco = toEnderecoDomain(dto.endereco());
        return new Cliente(
                dto.nome(),
                dto.cpf(),
                dto.dataNascimento(),
                endereco
        );
    }

    public ClienteResponseDto toClienteResponseDto(Cliente cliente) {
        if (cliente == null) return null;
        EnderecoResponseDto enderecoDto =
                toEnderecoResponseDto(cliente.getEndereco());
        return new ClienteResponseDto(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf().getDocument(),
                cliente.getDataNascimento(),
                enderecoDto
        );
    }

    public ClienteRequestDto toClienteRequestDto(Cliente cliente) {
        if (cliente == null) return null;
        EnderecoResponseDto enderecoDto =
                toEnderecoResponseDto(cliente.getEndereco());
        return new ClienteRequestDto(
                cliente.getNome(),
                cliente.getCpf().getDocument(),
                cliente.getDataNascimento(),
                toEnderecoRequestDto(enderecoDto)
        );

    }

    private EnderecoRequestDto toEnderecoRequestDto(EnderecoResponseDto endereco) {
        if (endereco == null) return null;
        return new EnderecoRequestDto(
                endereco.logradouro(),
                endereco.numero(),
                endereco.complemento(),
                endereco.bairro(),
                endereco.cidade(),
                endereco.estado(),
                endereco.cep()
        );
    }

    private EnderecoResponseDto toEnderecoResponseDto(Endereco endereco) {
        if (endereco == null) return null;
        return new EnderecoResponseDto(
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep().getDocument()
        );
    }

    private Endereco toEnderecoDomain(EnderecoRequestDto dto) {
        if (dto == null) return null;
        return new Endereco(
                dto.logradouro(),
                dto.numero(),
                dto.complemento(),
                dto.bairro(),
                dto.cidade(),
                dto.estado(),
                dto.cep()
        );
    }
}


