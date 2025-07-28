package core.domain.entities.endereco;

import core.domain.vo.CEP;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Endereco {

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    @Embedded
    @AttributeOverride(name = "document", column = @Column(name = "cep_document"))
    private CEP cep;

    public Endereco(){}

    public Endereco(String logradouro, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = new CEP(cep);
    }
}


