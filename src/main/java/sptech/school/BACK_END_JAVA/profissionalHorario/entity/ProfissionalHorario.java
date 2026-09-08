package sptech.school.BACK_END_JAVA.profissionalHorario.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(
        name = "profissional_horario",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_profissional_dia",
                columnNames = {"fk_profissional", "dia_semana"}
        )
)
public class ProfissionalHorario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_horario")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_profissional", nullable = false)
    @JsonIgnore
    private Profissional profissional;

    @Column(name = "dia_semana", nullable = false)
    private Integer diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    @Column(name = "intervalo_minutos", nullable = false)
    private Integer intervaloMinutos = 0;

    @Column(nullable = false)
    private Boolean ativo = true;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Profissional getProfissional() { return profissional; }
    public void setProfissional(Profissional profissional) { this.profissional = profissional; }
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
