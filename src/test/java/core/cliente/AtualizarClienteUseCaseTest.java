package core.cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

class AtualizarClienteUseCaseTest {

    private ClienteGateway clienteGateway;
    private AtualizarClienteUseCase useCase;

    @BeforeEach
    void setUp() {
        clienteGateway = mock(ClienteGateway.class);
        useCase = new AtualizarClienteUseCase(clienteGateway);
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        // Arrange
        Cliente cliente = criarCliente();
        Long id = 1L;

        when(clienteGateway.buscarPorId(id)).thenReturn(Optional.of(cliente));
        when(clienteGateway.atualizar(eq(id), any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente resultado = useCase.executar(id, cliente);

        // Assert
        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        verify(clienteGateway).buscarPorId(id);
        verify(clienteGateway).atualizar(id, cliente);
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        // Arrange
        Long id = 999L;
        Cliente cliente = criarCliente();

        when(clienteGateway.buscarPorId(id)).thenReturn(Optional.empty());

        // Act + Assert
        ClienteNaoEncontradoException ex = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> useCase.executar(id, cliente)
        );

        assertEquals("Cliente com ID 999 não encontrado.", ex.getMessage());
        verify(clienteGateway).buscarPorId(id);
        verify(clienteGateway, never()).atualizar(any(), any());
    }

    private Cliente criarCliente() {
        CPF cpf = new CPF();
        cpf.setDocument("12345678900");

        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João");
        cliente.setCpf(cpf);
        cliente.setDataNascimento(LocalDate.of(1990, 1, 1));
        cliente.setEndereco(null);
        return cliente;
    }
}