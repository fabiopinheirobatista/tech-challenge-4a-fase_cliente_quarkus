package core.cliente;

import core.domain.entities.cliente.Cliente;
import core.exception.ClienteNaoEncontradoException;
import core.gateways.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BuscarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public BuscarClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente executar(Long id) {
        return clienteGateway.buscarPorId(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
    }
}


