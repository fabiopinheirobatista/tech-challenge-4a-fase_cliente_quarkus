package core.cliente;

import core.domain.entities.cliente.Cliente;
import core.domain.vo.CPF;
import core.exception.CpfJaCadastradoException;
import core.gateways.ClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CriarClienteUseCaseTest {

    private ClienteGateway clienteGateway;
    private CriarClienteUseCase useCase;

    @BeforeEach
    void setUp() {
        clienteGateway = mock(ClienteGateway.class);
        useCase = new CriarClienteUseCase(clienteGateway);
    }

    @Test
    void deveCriarClienteQuandoCpfNaoCadastrado() {
        Cliente cliente = criarCliente();

        when(clienteGateway.buscarPorCpf(cliente.getCpf().getDocument()))
                .thenReturn(Optional.empty());
        when(clienteGateway.salvar(cliente)).thenReturn(cliente);

        Cliente resultado = useCase.executar(cliente);

        // Assert
        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        verify(clienteGateway).buscarPorCpf(cliente.getCpf().getDocument());
        verify(clienteGateway).salvar(cliente);
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastrado() {
        Cliente cliente = criarCliente();
        when(clienteGateway.buscarPorCpf(cliente.getCpf().getDocument()))
                .thenReturn(Optional.of(cliente));

        CpfJaCadastradoException ex = assertThrows(
                CpfJaCadastradoException.class,
                () -> useCase.executar(cliente)
        );

        assertEquals("Cliente com CPF 123.456.789-00 já cadastrado.", ex.getMessage());
        verify(clienteGateway).buscarPorCpf(cliente.getCpf().getDocument());
        verify(clienteGateway, never()).salvar(any());
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
