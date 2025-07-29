package core.cliente;

import core.exception.ClienteNaoEncontradoException;
import core.gateways.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeletarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public DeletarClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public void executar(Long id) {
        clienteGateway.buscarPorId(id).orElseThrow(() -> new ClienteNaoEncontradoException(id));
        clienteGateway.deletar(id);
    }

}


