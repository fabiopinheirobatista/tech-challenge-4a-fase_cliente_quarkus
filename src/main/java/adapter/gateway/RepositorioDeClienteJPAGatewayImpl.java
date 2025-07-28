package adapter.gateway;

import adapter.mapper.ClienteMapper;
import core.domain.entities.cliente.Cliente;
import core.gateways.ClienteGateway;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RepositorioDeClienteJPAGatewayImpl implements ClienteGateway {

    private final ClienteMapper mapper;

    public RepositorioDeClienteJPAGatewayImpl(ClienteMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Cliente salvar(Cliente cliente) {
        cliente.persist();
        return cliente;
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return Cliente.findByIdOptional(id);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return Cliente.find("cpf.document", cpf).firstResultOptional();
    }

    @Override
    @Transactional
    public void deletar(Long id) {
        Cliente.deleteById(id);
    }

    @Override
    @Transactional
    public Cliente atualizar(Long id, Cliente cliente) {
        Cliente entity = Cliente.findById(id);
        if (entity != null) {
            entity.setNome(cliente.getNome());
            entity.setCpf(cliente.getCpf());
            entity.setDataNascimento(cliente.getDataNascimento());
            entity.setEndereco(cliente.getEndereco());
            entity.persist();
        }
        return entity;
    }

    @Override
    public List<Cliente> listarTodos() {
        return Cliente.listAll();
    }
}


