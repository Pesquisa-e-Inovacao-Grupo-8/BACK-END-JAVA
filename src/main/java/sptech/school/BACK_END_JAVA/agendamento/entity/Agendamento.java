package sptech.school.BACK_END_JAVA.agendamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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

    @Column(nullable = false)
    private Double valorTotal;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    @Column
    private String nomeClienteAvulso;

    @Column
    private String telefoneClienteAvulso;

    @ManyToOne
    @JoinColumn(name = "fk_profissional")
    private Profissional profissional;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdemPedido() {
        return ordemPedido;
    }

    public void setOrdemPedido(String ordemPedido) {
        this.ordemPedido = ordemPedido;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNomeClienteAvulso() {
        return nomeClienteAvulso;
    }

    public void setNomeClienteAvulso(String nomeClienteAvulso) {
        this.nomeClienteAvulso = nomeClienteAvulso;
    }

    public String getTelefoneClienteAvulso() {
        return telefoneClienteAvulso;
    }

    public void setTelefoneClienteAvulso(String telefoneClienteAvulso) {
        this.telefoneClienteAvulso = telefoneClienteAvulso;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }
}
