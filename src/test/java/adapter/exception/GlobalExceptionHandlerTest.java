package adapter.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.junit.jupiter.api.Test;

import core.exception.CEPInvalidoException;
import core.exception.CPFInvalidoException;
import core.exception.ClienteNaoEncontradoException;
import core.exception.CpfJaCadastradoException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void deveTratarClienteNaoEncontradoException() {
        ClienteNaoEncontradoException ex = new ClienteNaoEncontradoException("Cliente não encontrado");
        Response response = handler.toResponse(ex);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    void deveTratarCepInvalidoException() {
        CEPInvalidoException ex = new CEPInvalidoException();
        Response response = handler.toResponse(ex);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void deveTratarCpfInvalidoException() {
        CPFInvalidoException ex = new CPFInvalidoException();
        Response response = handler.toResponse(ex);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    void deveTratarCpfJaCadastradoException() {
        CpfJaCadastradoException ex = new CpfJaCadastradoException("CPF já cadastrado");
        Response response = handler.toResponse(ex);

        assertEquals(Response.Status.CONFLICT.getStatusCode(), response.getStatus());
    }

    @Test
    void deveTratarConstraintViolationException() {
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        Path path = mock(Path.class);

        when(path.toString()).thenReturn("cpf");
        when(violation.getPropertyPath()).thenReturn(path);
        when(violation.getMessage()).thenReturn("CPF inválido");

        ConstraintViolationException ex = new ConstraintViolationException(Set.of(violation));
        Response response = handler.toResponse(ex);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }


    @Test
    void deveTratarRuntimeExceptionGenerica() {
        RuntimeException ex = new RuntimeException("Erro inesperado");
        Response response = handler.toResponse(ex);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
