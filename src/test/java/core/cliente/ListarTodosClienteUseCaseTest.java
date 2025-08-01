package core.cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.domain.entities.cliente.Cliente;
import core.domain.vo.CPF;
import core.gateways.ClienteGateway;

class ListarTodosClienteUseCaseTest {

    private ClienteGateway clienteGateway;
    private ListarTodosClienteUseCase useCase;

    @BeforeEach
    void setUp() {
        clienteGateway = mock(ClienteGateway.class);
        useCase = new ListarTodosClienteUseCase(clienteGateway);
    }

    @Test
    void deveListarTodosClientes() {
        Cliente cliente = criarCliente();
        when(clienteGateway.listarTodos()).thenReturn(List.of(cliente));

        List<Cliente> resultado = useCase.executar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
        verify(clienteGateway).listarTodos();
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
