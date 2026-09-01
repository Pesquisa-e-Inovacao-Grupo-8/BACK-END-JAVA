package sptech.school.BACK_END_JAVA.pacoteServico.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import java.util.UUID;

@Entity
@Getter
@Setter
public class PacoteServico {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_pacote_servico")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_pacote")
    private Pacote pacote;

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private Servico servico;
}
