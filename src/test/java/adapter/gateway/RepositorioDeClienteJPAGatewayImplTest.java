package adapter.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.mapper.ClienteMapper;
import core.domain.entities.cliente.Cliente;
import core.domain.vo.CPF;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.transaction.Transactional;

class RepositorioDeClienteJPAGatewayImplTest {

    private ClienteMapper mapper;
    private RepositorioDeClienteJPAGatewayImpl gateway;

    @BeforeEach
    void setUp() {
        mapper = mock(ClienteMapper.class);
        gateway = new RepositorioDeClienteJPAGatewayImpl(mapper);
    }
    
    @Test
    void deveSalvarCliente() {
        Cliente cliente = spy(criarCliente());
        doNothing().when(cliente).persist();

        Cliente salvo = gateway.salvar(cliente);

        assertNotNull(salvo);
        verify(cliente).persist();
        assertEquals("João", salvo.getNome());
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
