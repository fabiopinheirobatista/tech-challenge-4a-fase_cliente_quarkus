package core.cliente;

import core.domain.entities.cliente.Cliente;
import core.gateways.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ListarTodosClienteUseCase {

    private final ClienteGateway clienteGateway;

    public ListarTodosClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public List<Cliente> executar() {
        return clienteGateway.listarTodos();
    }
}


