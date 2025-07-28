package adapter.controller;

import adapter.controller.request.ClienteRequestDto;
import adapter.controller.response.ClienteResponseDto;
import adapter.mapper.ClienteMapper;
import core.cliente.AtualizarClienteUseCase;
import core.domain.entities.cliente.Cliente;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteAtualizarResource {

    @Inject
    AtualizarClienteUseCase atualizarClienteUseCase;
    @Inject
    ClienteMapper mapper;

    @PUT
    @Path("/{id}")
    public Response atualizarCliente(@PathParam("id") Long id, ClienteRequestDto dto) {
        Cliente clienteDomain = mapper.toClienteDomain(dto);
        Cliente atualizado = atualizarClienteUseCase.executar(id, clienteDomain);
        ClienteResponseDto responseDto = mapper.toClienteResponseDto(atualizado);
        return Response.ok(responseDto).build();
    }


}
