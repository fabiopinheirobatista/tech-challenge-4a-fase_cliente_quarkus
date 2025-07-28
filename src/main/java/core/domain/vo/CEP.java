package core.domain.vo;

import core.exception.CEPInvalidoException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class CEP {

    private String document;

    public CEP(){}

    public CEP(String document) {
        if (document == null || !document.matches("\\d{2}.\\d{3}-\\d{3}")) {
            throw new CEPInvalidoException();
        }
        this.document = document;
    }

    public String getDocument() {
        return document;
    }
}


