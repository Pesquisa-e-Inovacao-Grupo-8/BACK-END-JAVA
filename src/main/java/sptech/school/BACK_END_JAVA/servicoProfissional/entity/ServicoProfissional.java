package sptech.school.BACK_END_JAVA.servicoProfissional.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;

import java.util.UUID;

@Entity
@Getter
@Setter
public class ServicoProfissional {
    @Id
    @Column(name = "id_profissional_servico")
    private UUID id;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "fk_profissional")
    private Profissional profissional;
}
