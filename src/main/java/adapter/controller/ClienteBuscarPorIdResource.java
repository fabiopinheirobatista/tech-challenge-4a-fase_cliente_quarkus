package adapter.controller;

import adapter.controller.response.ClienteResponseDto;
import adapter.mapper.ClienteMapper;
import core.cliente.BuscarClienteUseCase;
import core.domain.entities.cliente.Cliente;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteBuscarPorIdResource {

    @Inject
    BuscarClienteUseCase buscarClienteUseCase;
    @Inject
    ClienteMapper mapper;

    @GET
    @Path("/{id}")
    public Response buscarClientePorId(@PathParam("id") Long id) {
        Cliente cliente = buscarClienteUseCase.executar(id);
        ClienteResponseDto responseDto = mapper.toClienteResponseDto(cliente);
        return Response.ok(responseDto).build();
    }

}
