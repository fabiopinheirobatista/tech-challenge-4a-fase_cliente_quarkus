package core.exception;

public class CEPInvalidoException extends RuntimeException {
    public CEPInvalidoException() {
        super("CEP no padrão incorreto!");
    }
}

