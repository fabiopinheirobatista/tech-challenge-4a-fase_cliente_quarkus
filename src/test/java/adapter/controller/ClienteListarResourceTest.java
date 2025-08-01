package adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import adapter.controller.response.ClienteResponseDto;
import adapter.controller.response.ClienteResponseDto.EnderecoResponseDto;
import adapter.mapper.ClienteMapper;
import core.cliente.ListarTodosClienteUseCase;
import core.domain.entities.cliente.Cliente;
import core.domain.vo.CPF;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

@QuarkusTest
class ClienteListarResourceTest {

    @InjectMocks
    ClienteListarResource resource;

    @Mock
    ListarTodosClienteUseCase listarTodosClienteUseCase;

    @Mock
    ClienteMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarListaDeClientesComStatus200() {
        CPF cpf = new CPF();
        cpf.setDocument("12345678900");
        
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João da Silva");
        cliente.setCpf(cpf);
        cliente.setDataNascimento(LocalDate.of(1990, 1, 1));

        ClienteResponseDto dto = new ClienteResponseDto(
                1L, "João da Silva", "12345678900", LocalDate.of(1990, 1, 1),
                new EnderecoResponseDto("Rua A", "123", "Ap 1", "Bairro", "Cidade", "Estado", "00000-000")
        );

        when(listarTodosClienteUseCase.executar()).thenReturn(List.of(cliente));
        when(mapper.toClienteResponseDto(cliente)).thenReturn(dto);

        Response response = resource.listarClientes();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        List<?> lista = (List<?>) response.getEntity();
        assertEquals(1, lista.size());
        assertEquals(dto, lista.get(0));
    }
}
