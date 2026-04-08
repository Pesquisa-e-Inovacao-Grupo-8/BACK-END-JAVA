package sptech.school.BACK_END_JAVA.clientePacote.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ClientePacote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fk_pacote")
    private Pacote pacote;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false)
    private LocalDateTime dtExpiracao;

    @Column(nullable = false)
    private Integer qtdUsos;

}
