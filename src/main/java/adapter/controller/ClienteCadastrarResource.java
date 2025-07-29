package adapter.controller;

import adapter.controller.request.ClienteRequestDto;
import adapter.controller.response.ClienteResponseDto;
import adapter.mapper.ClienteMapper;
import core.cliente.*;
import core.domain.entities.cliente.Cliente;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteCadastrarResource {

    @Inject
    CriarClienteUseCase criarClienteUseCase;
    @Inject
    ClienteMapper mapper;

    @POST
    public Response cadastrarCliente(ClienteRequestDto dto) {
        Cliente cliente = mapper.toClienteDomain(dto);
        Cliente salvo = criarClienteUseCase.executar(cliente);
        ClienteResponseDto responseDto = mapper.toClienteResponseDto(salvo);
        return Response.status(Response.Status.CREATED).entity(responseDto).build();
    }
}
