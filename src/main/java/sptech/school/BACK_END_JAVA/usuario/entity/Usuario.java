package sptech.school.BACK_END_JAVA.usuario.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_usuario")
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    //mudar pra enum
    private String tipo;

    @Column(nullable = false)
    //mudar pra enum
    private Boolean ativo;

    @Column(nullable = false)
    private LocalDateTime criacao;
}
