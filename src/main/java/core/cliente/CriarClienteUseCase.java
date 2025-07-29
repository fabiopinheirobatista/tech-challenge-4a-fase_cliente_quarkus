package core.cliente;

import core.domain.entities.cliente.Cliente;
import core.exception.CpfJaCadastradoException;
import core.gateways.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class CriarClienteUseCase {

    private final ClienteGateway clienteGateway;

    public CriarClienteUseCase(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    public Cliente executar(Cliente cliente) {

        Optional<Cliente> clienteCpf = clienteGateway.buscarPorCpf(cliente.getCpf().getDocument());
        if (clienteCpf.isPresent()) {
            throw new CpfJaCadastradoException(cliente.getCpf().getDocument());
        }
        return clienteGateway.salvar(cliente);
    }
}


