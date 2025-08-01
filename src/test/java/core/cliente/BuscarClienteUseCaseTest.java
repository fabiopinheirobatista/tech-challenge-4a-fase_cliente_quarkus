package core.cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
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

class BuscarClienteUseCaseTest {

    private ClienteGateway clienteGateway;
    private BuscarClienteUseCase useCase;

    @BeforeEach
    void setUp() {
        clienteGateway = mock(ClienteGateway.class);
        useCase = new BuscarClienteUseCase(clienteGateway);
    }

    @Test
    void deveRetornarClienteQuandoEncontrado() {
        Cliente cliente = criarCliente();
        Long id = 1L;

        when(clienteGateway.buscarPorId(id)).thenReturn(Optional.of(cliente));

        Cliente resultado = useCase.executar(id);

        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        verify(clienteGateway).buscarPorId(id);
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        Long id = 999L;
        when(clienteGateway.buscarPorId(id)).thenReturn(Optional.empty());

        ClienteNaoEncontradoException ex = assertThrows(
                ClienteNaoEncontradoException.class,
                () -> useCase.executar(id)
        );

        assertEquals("Cliente com ID 999 não encontrado.", ex.getMessage());
        verify(clienteGateway).buscarPorId(id);
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
