package sptech.school.BACK_END_JAVA.profissional.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Profissional {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_profissional")
    private UUID id;

    @Column(nullable = false)
    private String especialidade;

    @Column(nullable = true)
    private String descricao;

    @Column(nullable = true)
    private String foto;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "profissional_servico",
            joinColumns = @JoinColumn(name = "profissional_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private java.util.List<sptech.school.BACK_END_JAVA.servico.entity.Servico> servicos;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public java.util.List<sptech.school.BACK_END_JAVA.servico.entity.Servico> getServicos() {
        return servicos;
    }

    public void setServicos(java.util.List<sptech.school.BACK_END_JAVA.servico.entity.Servico> servicos) {
        this.servicos = servicos;
    }

}
