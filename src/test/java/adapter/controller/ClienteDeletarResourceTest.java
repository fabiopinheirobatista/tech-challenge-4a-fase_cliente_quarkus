package adapter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import core.cliente.DeletarClienteUseCase;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;

@QuarkusTest
class ClienteDeletarResourceTest {

    @InjectMocks
    ClienteDeletarResource resource;

    @Mock
    DeletarClienteUseCase deletarClienteUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveDeletarClienteComSucessoERetornar204() {
        Long id = 1L;
        doNothing().when(deletarClienteUseCase).executar(id);

        Response response = resource.deletarCliente(id);

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(deletarClienteUseCase).executar(id);
    }
}
