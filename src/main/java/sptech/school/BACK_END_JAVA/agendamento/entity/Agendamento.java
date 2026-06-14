package sptech.school.BACK_END_JAVA.agendamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.clientePacote.entity.ClientePacote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_agendamento")
    private UUID id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFim;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String ordemPedido;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fk_profissional")
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_pacote")
    private ClientePacote clientePacote;

}
