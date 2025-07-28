package core.domain.entities.cliente;

import core.domain.entities.endereco.Endereco;
import core.domain.vo.CPF;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "clientes")
public class Cliente extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Embedded
    @AttributeOverride(name = "document", column = @Column(name = "cpf_document"))
    private CPF cpf;
    private LocalDate dataNascimento;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "logradouro", column = @Column(name = "endereco_logradouro")),
            @AttributeOverride(name = "numero", column = @Column(name = "endereco_numero")),
            @AttributeOverride(name = "complemento", column = @Column(name = "endereco_complemento")),
            @AttributeOverride(name = "bairro", column = @Column(name = "endereco_bairro")),
            @AttributeOverride(name = "cidade", column = @Column(name = "endereco_cidade")),
            @AttributeOverride(name = "estado", column = @Column(name = "endereco_estado")),
            @AttributeOverride(name = "document", column = @Column(name = "endereco_cep"))
    })
    private Endereco endereco;


    public Cliente(){}

    public Cliente(Long id, String nome, String cpf, LocalDate dataNascimento, Endereco endereco) {
        this(nome, cpf, dataNascimento, endereco);
        this.id = id;
    }

    public Cliente(String nome, String cpf, LocalDate dataNascimento, Endereco endereco) {
        this.nome = nome;
        this.cpf = new CPF(cpf);
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }
}


