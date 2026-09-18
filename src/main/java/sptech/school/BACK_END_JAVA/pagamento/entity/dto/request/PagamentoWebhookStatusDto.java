package sptech.school.BACK_END_JAVA.pagamento.entity.dto.request;

import jakarta.validation.constraints.NotBlank;

public class PagamentoWebhookStatusDto {

    @NotBlank
    private String statusPagamento;

    @NotBlank
    private String statusAgendamento;

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public String getStatusAgendamento() {
        return statusAgendamento;
    }

    public void setStatusAgendamento(String statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
    }
}