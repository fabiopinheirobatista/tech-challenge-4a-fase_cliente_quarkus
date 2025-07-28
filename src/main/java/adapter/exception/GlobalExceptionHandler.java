package adapter.exception;

import core.exception.CEPInvalidoException;
import core.exception.CPFInvalidoException;
import core.exception.ClienteNaoEncontradoException;
import core.exception.CpfJaCadastradoException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.validation.ConstraintViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        if (exception instanceof ClienteNaoEncontradoException) {
            return handleClienteNaoEncontradoException((ClienteNaoEncontradoException) exception);
        } else if (exception instanceof CEPInvalidoException) {
            return handleCEPInvalidoException((CEPInvalidoException) exception);
        } else if (exception instanceof CPFInvalidoException) {
            return handleCPFInvalidoException((CPFInvalidoException) exception);
        } else if (exception instanceof CpfJaCadastradoException) {
            return handleCpfJaCadastradoException((CpfJaCadastradoException) exception);
        } else if (exception instanceof ConstraintViolationException) {
            return handleConstraintViolationException((ConstraintViolationException) exception);
        }
        // Default handler for other RuntimeExceptions
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Problem.builder()
                        .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                        .title("Erro interno do servidor")
                        .detail(exception.getMessage())
                        .userMessage("Ocorreu um erro interno no servidor. Tente novamente mais tarde.")
                        .timestamp(LocalDateTime.now())
                        .build())
                .build();
    }

    private Response handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex) {
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(Response.Status.NOT_FOUND, problemType, detail)
                .userMessage(detail)
                .build();
        return Response.status(Response.Status.NOT_FOUND).entity(problem).build();
    }

    private Response handleCEPInvalidoException(CEPInvalidoException ex) {
        ProblemType problemType = ProblemType.CEP_INVALIDOS;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(Response.Status.BAD_REQUEST, problemType, detail)
                .userMessage(detail)
                .build();
        return Response.status(Response.Status.BAD_REQUEST).entity(problem).build();
    }

    private Response handleCPFInvalidoException(CPFInvalidoException ex) {
        ProblemType problemType = ProblemType.CPF_INVALIDOS;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(Response.Status.BAD_REQUEST, problemType, detail)
                .userMessage(detail)
                .build();
        return Response.status(Response.Status.BAD_REQUEST).entity(problem).build();
    }

    private Response handleCpfJaCadastradoException(CpfJaCadastradoException ex) {
        ProblemType problemType = ProblemType.CPF_JA_CADASTRADO;
        String detail = ex.getMessage();
        Problem problem = createProblemBuilder(Response.Status.CONFLICT, problemType, detail)
                .userMessage(detail)
                .build();
        return Response.status(Response.Status.CONFLICT).entity(problem).build();
    }

    private Response handleConstraintViolationException(ConstraintViolationException ex) {
        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<Problem.Field> problemFields = ex.getConstraintViolations().stream()
                .map(violation -> Problem.Field.builder()
                        .name(violation.getPropertyPath().toString())
                        .userMessage(violation.getMessage())
                        .build())
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(Response.Status.BAD_REQUEST, problemType, detail)
                .userMessage(detail)
                .fields(problemFields)
                .build();

        return Response.status(Response.Status.BAD_REQUEST).entity(problem).build();
    }

    private Problem.ProblemBuilder createProblemBuilder(Response.Status status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.getStatusCode())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .timestamp(LocalDateTime.now())
                .detail(detail);
    }
}


