package core.cliente;

import core.domain.entities.cliente.Cliente;
import core.exception.ClienteNaoEncontradoException;
import core.gateways.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AtualizarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public AtualizarClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente executar(Long id, Cliente cliente) {
        clienteGateway
                .buscarPorId(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException(id));

        return clienteGateway.atualizar(id, cliente);
    }

}


