package sptech.school.BACK_END_JAVA.agendamento.entity.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AgendamentoRequestDto {

    private Integer clienteId;

    private String nomeClienteAvulso;

    private String telefoneClienteAvulso;

    private String status;

    private LocalDate data;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private Integer profissionalId;

    private Integer servicoId;

    private List<Integer> servicos;

    public AgendamentoRequestDto(Integer clienteId, String nomeClienteAvulso, String telefoneClienteAvulso, String status, LocalDate data, LocalTime horaInicio, LocalTime horaFim, Integer profissionalId, Integer servicoId, List<Integer> servicos) {
        this.clienteId = clienteId;
        this.nomeClienteAvulso = nomeClienteAvulso;
        this.telefoneClienteAvulso = telefoneClienteAvulso;
        this.status = status;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.profissionalId = profissionalId;
        this.servicoId = servicoId;
        this.servicos = servicos;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Integer getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Integer profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Integer getServicoId() {
        return servicoId;
    }

    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }

    public List<Integer> getServicos() {
        return servicos;
    }

    public void setServicos(List<Integer> servicos) {
        this.servicos = servicos;
    }
}


