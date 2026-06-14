package sptech.school.BACK_END_JAVA.profissional.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Profissional {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_profissional")
    private UUID id;

    @Column(nullable = false)
    private String especialidade;

    @Column(nullable = true)
    private String descricao;

    @Column(nullable = true)
    private String foto;

    @ManyToOne
    @JoinColumn(name = "fk_usuario")
    private Usuario usuario;
}
