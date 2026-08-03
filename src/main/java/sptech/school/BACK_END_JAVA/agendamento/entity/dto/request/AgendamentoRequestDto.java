package sptech.school.BACK_END_JAVA.agendamento.entity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class AgendamentoRequestDto {

    private UUID clienteId;

    private String nomeClienteAvulso;

    private String telefoneClienteAvulso;

    private String status;

    private LocalDate data;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private UUID profissionalId;

    private UUID servicoId;

    private List<UUID> servicos;

    public AgendamentoRequestDto(UUID clienteId, String nomeClienteAvulso, String telefoneClienteAvulso, String status, LocalDate data, LocalTime horaInicio, LocalTime horaFim, UUID profissionalId, UUID servicoId, List<UUID> servicos) {
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

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
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

    public UUID getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(UUID profissionalId) {
        this.profissionalId = profissionalId;
    }

    public UUID getServicoId() {
        return servicoId;
    }

    public void setServicoId(UUID servicoId) {
        this.servicoId = servicoId;
    }

    public List<UUID> getServicos() {
        return servicos;
    }

    public void setServicos(List<UUID> servicos) {
        this.servicos = servicos;
    }
}
