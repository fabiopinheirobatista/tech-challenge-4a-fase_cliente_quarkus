package core.gateways;

import core.domain.entities.cliente.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteGateway {

    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    Optional<Cliente> buscarPorCpf(String cpf);
    void deletar(Long id);
    Cliente atualizar(Long id, Cliente cliente);
    List<Cliente> listarTodos();
}


