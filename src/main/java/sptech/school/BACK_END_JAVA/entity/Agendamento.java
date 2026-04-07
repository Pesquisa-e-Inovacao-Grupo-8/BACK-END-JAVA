package sptech.school.BACK_END_JAVA.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 120)
    private String cliente;

    @Column(nullable = false, length = 120)
    private String servico;

    @Column(nullable = false, length = 80)
    private String funcionaria;

    @Column(nullable = false, precision = 10, scale = 2)
    private Integer preco;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private Integer dia;

    @Column(nullable = false)
    private Integer mes;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false, length = 20)
    private String status; // PENDENTE | AGENDADO | FINALIZADO | CANCELADO

    @Column(nullable = false, length = 20)
    private String pagamento; // PENDENTE | PAGO

    @Column(nullable = false)
    private Integer duracaoMinutos;

    @Column(nullable = false)
    private Boolean pagamentoAdiantado = false;

    @Column(nullable = false, unique = true, length = 120)
    private String ordemAgendamento;

    @Column(length = 500)
    private String linkPagamento;

    @PrePersist
    public void prePersist() {
        if (pagamentoAdiantado == null) pagamentoAdiantado = false;
        if (status == null || status.isBlank()) status = "PENDENTE";
        if (pagamento == null || pagamento.isBlank()) pagamento = "PENDENTE";

        if (data != null) {
            if (dia == null) dia = data.getDayOfMonth();
            if (mes == null) mes = data.getMonthValue();
            if (ano == null) ano = data.getYear();
        }
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }

    public String getServico() { return servico; }
    public void setServico(String servico) { this.servico = servico; }

    public String getFuncionaria() { return funcionaria; }
    public void setFuncionaria(String funcionaria) { this.funcionaria = funcionaria; }

    public Integer getPreco() { return preco; }
    public void setPreco(Integer preco) { this.preco = preco; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public Integer getDia() { return dia; }
    public void setDia(Integer dia) { this.dia = dia; }

    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPagamento() { return pagamento; }
    public void setPagamento(String pagamento) { this.pagamento = pagamento; }

    public Integer getDuracaoMinutos() { return duracaoMinutos; }
    public void setDuracaoMinutos(Integer duracaoMinutos) { this.duracaoMinutos = duracaoMinutos; }

    public Boolean getPagamentoAdiantado() { return pagamentoAdiantado; }
    public void setPagamentoAdiantado(Boolean pagamentoAdiantado) { this.pagamentoAdiantado = pagamentoAdiantado; }

    public String getOrdemAgendamento() { return ordemAgendamento; }
    public void setOrdemAgendamento(String ordemAgendamento) { this.ordemAgendamento = ordemAgendamento; }

    public String getLinkPagamento() { return linkPagamento; }
    public void setLinkPagamento(String linkPagamento) { this.linkPagamento = linkPagamento; }
}