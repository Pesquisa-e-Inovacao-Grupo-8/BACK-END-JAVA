package sptech.school.BACK_END_JAVA.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "funcionaria")
public class Funcionaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 120)
    private String especialidade;

    @Column(nullable = false)
    private boolean ativo;

    @ElementCollection
    @CollectionTable(name = "funcionaria_servicos",
            joinColumns = @JoinColumn(name = "funcionaria_id"))
    @Column(name = "servico")
    private List<String> servicos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<String> getServicos() {
        return servicos;
    }

    public void setServicos(List<String> servicos) {
        this.servicos = servicos;
    }
}