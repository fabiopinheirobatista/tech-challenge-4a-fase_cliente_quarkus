package adapter.controller;

import adapter.controller.response.ClienteResponseDto;
import adapter.mapper.ClienteMapper;
import core.cliente.ListarTodosClienteUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteListarResource {

    @Inject
    ListarTodosClienteUseCase listarTodosClienteUseCase;
    @Inject
    ClienteMapper mapper;

    @GET
    public Response listarClientes() {
        List<ClienteResponseDto> clientes = listarTodosClienteUseCase.executar().stream()
                .map(mapper::toClienteResponseDto)
                .collect(Collectors.toList());
        return Response.ok(clientes).build();
    }

}
