package sptech.school.BACK_END_JAVA.agendamentoServico.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.clientePacoteServico.entity.ClientePacoteServico;

import java.util.UUID;

@Entity
public class AgendamentoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_agendamento_servico")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_agendamento")
    private Agendamento agendamento;

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_pacote_servico")
    private ClientePacoteServico clientePacoteServico;
}
