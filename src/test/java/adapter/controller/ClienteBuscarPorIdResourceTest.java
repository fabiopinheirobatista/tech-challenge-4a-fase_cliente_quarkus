package adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import adapter.controller.response.ClienteResponseDto;
import adapter.controller.response.ClienteResponseDto.EnderecoResponseDto;
import adapter.mapper.ClienteMapper;
import core.cliente.BuscarClienteUseCase;
import core.domain.entities.cliente.Cliente;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

@QuarkusTest
class ClienteBuscarPorIdResourceTest {

    @InjectMocks
    ClienteBuscarPorIdResource resource;

    @Mock
    BuscarClienteUseCase buscarClienteUseCase;

    @Mock
    ClienteMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveBuscarClientePorIdERetornarOk() {

        Long id = 1L;
        Cliente clienteMock = new Cliente();
        EnderecoResponseDto enderecoDto = new EnderecoResponseDto(
                "Rua das Flores", "123", "Apto 45", "Centro",
                "São Paulo", "SP", "01234-567"
        );
        ClienteResponseDto responseDtoMock = new ClienteResponseDto(
                id,
                "João da Silva",
                "123.456.789-00",
                LocalDate.of(1990, 1, 1),
                enderecoDto
        );

        when(buscarClienteUseCase.executar(id)).thenReturn(clienteMock);
        when(mapper.toClienteResponseDto(clienteMock)).thenReturn(responseDtoMock);


        Response response = resource.buscarClientePorId(id);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(responseDtoMock, response.getEntity());
    }
}
