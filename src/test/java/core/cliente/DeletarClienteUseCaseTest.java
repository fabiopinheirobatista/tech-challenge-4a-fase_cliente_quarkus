package core.cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.domain.entities.cliente.Cliente;
import core.domain.vo.CPF;
import core.exception.ClienteNaoEncontradoException;
import core.gateways.ClienteGateway;

class DeletarClienteUseCaseTest {

    private ClienteGateway clienteGateway;
    private DeletarClienteUseCase useCase;

    @BeforeEach
    void setUp() {
        clienteGateway = mock(ClienteGateway.class);
        useCase = new DeletarClienteUseCase(clienteGateway);
    }

    @Test
    void deveDeletarClienteQuandoExistente() {
        // Arrange
        Cliente cliente = criarCliente();
        when(clienteGateway.buscarPorId(cliente.getId())).thenReturn(Optional.of(cliente));
        doNothing().when(clienteGateway).deletar(cliente.getId());

        useCase.executar(cliente.getId());

        verify(clienteGateway).buscarPorId(cliente.getId());
        verify(clienteGateway).deletar(cliente.getId());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        Long id = 99L;
        when(clienteGateway.buscarPorId(id)).thenReturn(Optional.empty());

        ClienteNaoEncontradoException ex = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> useCase.executar(id)
        );

        assertEquals("Cliente com ID 99 não encontrado.", ex.getMessage());
        verify(clienteGateway).buscarPorId(id);
        verify(clienteGateway, never()).deletar(anyLong());
    }

    private Cliente criarCliente() {
        CPF cpf = new CPF();
        cpf.setDocument("123.456.789-00");

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João");
        cliente.setCpf(cpf);
        cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
        cliente.setEndereco(null);
        return cliente;
    }
}
