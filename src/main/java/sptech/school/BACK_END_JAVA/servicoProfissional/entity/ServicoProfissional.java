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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "fk_profissional")
    private Profissional profissional;
}
