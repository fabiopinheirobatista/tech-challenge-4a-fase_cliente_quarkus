package core.exception;

public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String cpf) {
        super("Cliente com CPF " + cpf + " já cadastrado.");
    }
}


