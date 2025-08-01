package adapter.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import adapter.controller.request.ClienteRequestDto;
import adapter.controller.response.ClienteResponseDto;
import core.domain.entities.cliente.Cliente;
import core.domain.entities.endereco.Endereco;
import core.domain.vo.CEP;
import core.domain.vo.CPF;

public class ClienteMapperTest {

    private final ClienteMapper mapper = new ClienteMapper();

    @Test
    void deveMapearClienteRequestDtoParaClienteDomain() {
        ClienteRequestDto.EnderecoRequestDto enderecoDto = new ClienteRequestDto.EnderecoRequestDto(
                "Rua A", "123", "Apto 1", "Centro", "São Paulo", "SP", "99.999-999"
        );

        ClienteRequestDto dto = new ClienteRequestDto(
                "João Silva", "999.999.999-99", LocalDate.of(1990, 1, 1), enderecoDto
        );

        Cliente cliente = mapper.toClienteDomain(dto);

        assertEquals("João Silva", cliente.getNome());
        assertEquals("999.999.999-99", cliente.getCpf().getDocument());
        assertEquals(LocalDate.of(1990, 1, 1), cliente.getDataNascimento());
        assertEquals("Rua A", cliente.getEndereco().getLogradouro());
        assertEquals("99.999-999", cliente.getEndereco().getCep().getDocument());
    }

    @Test
    void deveMapearClienteDomainParaClienteResponseDto() {
    	CEP cep = new CEP();
    	cep.setDocument("99.999-999");
    	
       	CPF cpf = new CPF();
    	cpf.setDocument("999.999.999-99");
    	
        Endereco endereco = new Endereco(
                "Rua B", "456", "Casa", "Bairro B", "Rio", "RJ", cep.getDocument()
        );

        Cliente cliente = new Cliente(
                1L,
                "Maria Souza",
                cpf.getDocument(),
                LocalDate.of(1985, 5, 20),
                endereco
        );

        ClienteResponseDto dto = mapper.toClienteResponseDto(cliente);

        assertEquals(1L, dto.id());
        assertEquals("Maria Souza", dto.nome());
        assertEquals("999.999.999-99", dto.cpf());
        assertEquals("Rua B", dto.endereco().logradouro());
        assertEquals("99.999-999", dto.endereco().cep());
    }

    @Test
    void deveMapearClienteDomainParaClienteRequestDto() {
     	CEP cep = new CEP();
    	cep.setDocument("99.999-999");
    	
    	CPF cpf = new CPF();
    	cpf.setDocument("999.999.999-99");
    	
        Endereco endereco = new Endereco(
                "Av X", "999", "Bloco C", "Centro", "Fortaleza", "CE", cep.getDocument()
        );

        Cliente cliente = new Cliente(
                "Carlos Lima",
                cpf.getDocument(),
                LocalDate.of(2000, 12, 12),
                endereco
        );

        ClienteRequestDto dto = mapper.toClienteRequestDto(cliente);

        assertEquals("Carlos Lima", dto.nome());
        assertEquals("999.999.999-99", dto.cpf());
        assertEquals("Av X", dto.endereco().logradouro());
        assertEquals("99.999-999", dto.endereco().cep());
    }
}
