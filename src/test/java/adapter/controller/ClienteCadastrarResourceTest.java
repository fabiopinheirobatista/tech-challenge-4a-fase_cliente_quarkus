package adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import adapter.controller.request.ClienteRequestDto;
import adapter.controller.request.ClienteRequestDto.EnderecoRequestDto;
import adapter.controller.response.ClienteResponseDto;
import adapter.controller.response.ClienteResponseDto.EnderecoResponseDto;
import adapter.mapper.ClienteMapper;
import core.cliente.CriarClienteUseCase;
import core.domain.entities.cliente.Cliente;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

@QuarkusTest
class ClienteCadastrarResourceTest {

    @InjectMocks
    ClienteCadastrarResource resource;

    @Mock
    CriarClienteUseCase criarClienteUseCase;

    @Mock
    ClienteMapper mapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarClienteComSucessoERetornar201() {
        EnderecoRequestDto enderecoRequest = new EnderecoRequestDto(
                "Rua XPTO", "123", "Apto 4", "Centro", "São Paulo", "SP", "12345-000"
        );

        ClienteRequestDto requestDto = new ClienteRequestDto(
                "Maria Oliveira", "123.456.789-00", LocalDate.of(1985, 5, 20), enderecoRequest
        );

        Cliente clienteDomain = new Cliente();
        Cliente clienteSalvo = new Cliente();

        EnderecoResponseDto enderecoResponse = new EnderecoResponseDto(
                "Rua XPTO", "123", "Apto 4", "Centro", "São Paulo", "SP", "12345-000"
        );

        ClienteResponseDto responseDto = new ClienteResponseDto(
                1L, "Maria Oliveira", "123.456.789-00", LocalDate.of(1985, 5, 20), enderecoResponse
        );

        when(mapper.toClienteDomain(requestDto)).thenReturn(clienteDomain);
        when(criarClienteUseCase.executar(clienteDomain)).thenReturn(clienteSalvo);
        when(mapper.toClienteResponseDto(clienteSalvo)).thenReturn(responseDto);

        Response response = resource.cadastrarCliente(requestDto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(responseDto, response.getEntity());
    }
}
