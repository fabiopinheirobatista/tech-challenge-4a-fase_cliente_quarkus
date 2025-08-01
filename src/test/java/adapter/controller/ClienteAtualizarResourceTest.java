package adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.controller.request.ClienteRequestDto;
import adapter.controller.request.ClienteRequestDto.EnderecoRequestDto;
import adapter.controller.response.ClienteResponseDto;
import adapter.mapper.ClienteMapper;
import core.cliente.AtualizarClienteUseCase;
import core.domain.entities.cliente.Cliente;
import jakarta.ws.rs.core.Response;

class ClienteAtualizarResourceTest {

    private ClienteAtualizarResource resource;
    private AtualizarClienteUseCase atualizarClienteUseCase;
    private ClienteMapper mapper;

    @BeforeEach
    void setUp() {
        atualizarClienteUseCase = mock(AtualizarClienteUseCase.class);
        mapper = mock(ClienteMapper.class);
        resource = new ClienteAtualizarResource();
        resource.atualizarClienteUseCase = atualizarClienteUseCase;
        resource.mapper = mapper;
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        Long id = 1L;
        ClienteRequestDto.EnderecoRequestDto enderecoDto = new EnderecoRequestDto(
                "Rua A", "123", "Apto 1", "Centro", "São Paulo", "SP", "01000-000"
        );

        ClienteRequestDto requestDto = new ClienteRequestDto(
                "João da Silva", "12345678901", LocalDate.of(1990, 1, 1), enderecoDto
        );

        Cliente clienteDomain = new Cliente();
        Cliente clienteAtualizado = new Cliente();
        ClienteResponseDto responseDto = new ClienteResponseDto(1L, "João da Silva", "12345678901", LocalDate.of(1990, 1, 1), null);

        when(mapper.toClienteDomain(requestDto)).thenReturn(clienteDomain);
        when(atualizarClienteUseCase.executar(id, clienteDomain)).thenReturn(clienteAtualizado);
        when(mapper.toClienteResponseDto(clienteAtualizado)).thenReturn(responseDto);

        Response response = resource.atualizarCliente(id, requestDto);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(responseDto, response.getEntity());
        verify(atualizarClienteUseCase, times(1)).executar(id, clienteDomain);
    }
}
