package sptech.school.BACK_END_JAVA.comprovante.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.pagamento.entity.Pagamento;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Comprovante {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "fk_pagamento")
    private Pagamento pagamento;
}
