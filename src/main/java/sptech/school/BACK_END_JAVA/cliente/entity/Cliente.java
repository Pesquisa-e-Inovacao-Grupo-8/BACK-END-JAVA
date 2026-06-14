package sptech.school.BACK_END_JAVA.cliente.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_cliente")
    private UUID id;

    @Column(nullable = true)
    private String observacoes;

    @OneToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
}
