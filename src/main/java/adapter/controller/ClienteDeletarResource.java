package adapter.controller;

import core.cliente.DeletarClienteUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteDeletarResource {

    @Inject
    DeletarClienteUseCase deletarClienteUseCase;

    @DELETE
    @Path("/{id}")
    public Response deletarCliente(@PathParam("id") Long id) {
        deletarClienteUseCase.executar(id);
        return Response.noContent().build();
    }
}


