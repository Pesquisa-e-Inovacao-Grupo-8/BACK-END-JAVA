package sptech.school.BACK_END_JAVA.clientePacote.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDtExpiracao() {
        return dtExpiracao;
    }

    public void setDtExpiracao(LocalDateTime dtExpiracao) {
        this.dtExpiracao = dtExpiracao;
    }

    public Integer getQtdUsos() {
        return qtdUsos;
    }

    public void setQtdUsos(Integer qtdUsos) {
        this.qtdUsos = qtdUsos;
    }
}
