package sptech.school.BACK_END_JAVA.pagamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private String metodo;

    @Column(nullable = false)
    //mudar para ENUM
    private String status;

    @Column(nullable = false)
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "fk_agendamento")
    private Agendamento agendamento;
}
