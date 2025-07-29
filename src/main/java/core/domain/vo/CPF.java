package core.domain.vo;

import core.exception.CPFInvalidoException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class CPF {

    private String document;

    public CPF(){}

    public CPF(String document) {
        if (document == null || !document.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new CPFInvalidoException();
        }
        this.document = document;
    }

    @Override
    public String toString() {
        return this.document;
    }
}


