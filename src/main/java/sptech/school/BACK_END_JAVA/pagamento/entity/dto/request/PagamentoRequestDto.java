package sptech.school.BACK_END_JAVA.pagamento.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class PagamentoRequestDto {

    @NotNull
    @Schema(description = "Representa o valor a ser pago", example = "159.99")
    private Double valor;

    @NotBlank
    @Schema(description = "Representa a método cujo pagamento será efetuado", example = "Pix")
    private String metodo;

    @NotBlank
    @Schema(description = "Representa o estado do pagamento", example = "PE")
    private String status;

    @NotNull
    @Schema(description = "Representa o momento em que é gerado o pagamento", example = "2026-12-31T23:59:59")
    private LocalDateTime data;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do agendamento", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private Integer agendamentoId;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Integer getAgendamentoId() {
        return agendamentoId;
    }

    public void setAgendamentoId(Integer agendamentoId) {
        this.agendamentoId = agendamentoId;
    }
}
