package sptech.school.BACK_END_JAVA.profissionalHorario.entity.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class ProfissionalHorarioRequestDto {
    @NotNull private Integer diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Integer intervaloMinutos;
    private Boolean ativo;

    public Integer getDiaSemana() { return diaSemana; }
    public void setDiaSemana(Integer diaSemana) { this.diaSemana = diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }
    public Integer getIntervaloMinutos() { return intervaloMinutos; }
    public void setIntervaloMinutos(Integer intervaloMinutos) { this.intervaloMinutos = intervaloMinutos; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}