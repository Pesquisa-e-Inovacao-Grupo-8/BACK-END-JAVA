package sptech.school.BACK_END_JAVA.clientePacoteServico.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.clientePacote.entity.ClientePacote;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ClientePacoteServico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_cliente_pacote_servico")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_pacote")
    private ClientePacote clientePacote;

    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private Servico servico;

    @Column(nullable = false)
    private Integer quantidadeDisponivel;


}