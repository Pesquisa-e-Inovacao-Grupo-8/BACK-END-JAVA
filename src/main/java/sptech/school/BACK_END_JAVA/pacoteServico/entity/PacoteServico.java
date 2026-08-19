package sptech.school.BACK_END_JAVA.pacoteServico.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;

@Entity
@Getter
@Setter
public class PacoteServico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pacote_servico")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_pacote")
    private Pacote pacote;

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private Servico servico;
}
