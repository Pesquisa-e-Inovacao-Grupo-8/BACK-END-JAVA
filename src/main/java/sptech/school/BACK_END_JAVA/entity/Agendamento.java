package sptech.school.BACK_END_JAVA.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String cliente;

    @Column(nullable = false)
    private String servico;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private Integer duracaoMinutos;

    @Column(nullable = false)
    private String funcionaria;

    @Column(nullable = false)
    private Boolean pagamentoAdiantado;

    @Column(nullable = false)
    private String ordemAgendamento;


    @Column(nullable = false)
    private String pagamentoStatus; // PAGO | PENDENTE

    @Column(nullable = false)
    private String status; // AGENDADO | FINALIZADO | CANCELADO

    private String linkPagamento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getFuncionaria() {
        return funcionaria;
    }

    public void setFuncionaria(String funcionaria) {
        this.funcionaria = funcionaria;
    }

    public Boolean getPagamentoAdiantado() {
        return pagamentoAdiantado;
    }

    public void setPagamentoAdiantado(Boolean pagamentoAdiantado) {
        this.pagamentoAdiantado = pagamentoAdiantado;
    }

    public String getOrdemAgendamento() {
        return ordemAgendamento;
    }

    public void setOrdemAgendamento(String ordemAgendamento) {
        this.ordemAgendamento = ordemAgendamento;
    }

    public String getPagamentoStatus() {
        return pagamentoStatus;
    }

    public void setPagamentoStatus(String pagamentoStatus) {
        this.pagamentoStatus = pagamentoStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLinkPagamento() {
        return linkPagamento;
    }

    public void setLinkPagamento(String linkPagamento) {
        this.linkPagamento = linkPagamento;
    }
    
}