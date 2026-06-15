package sptech.school.BACK_END_JAVA.comprovante.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.pagamento.entity.Pagamento;

import java.util.UUID;

@Entity
public class Comprovante {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_comprovante")
    private UUID id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "fk_pagamento")
    private Pagamento pagamento;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
