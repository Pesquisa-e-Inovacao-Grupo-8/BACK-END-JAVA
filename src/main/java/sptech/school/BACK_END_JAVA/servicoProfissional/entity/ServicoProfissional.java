package sptech.school.BACK_END_JAVA.servicoProfissional.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;

@Entity
@Getter
@Setter
public class ServicoProfissional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profissional_servico")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "fk_profissional")
    private Profissional profissional;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }
}
