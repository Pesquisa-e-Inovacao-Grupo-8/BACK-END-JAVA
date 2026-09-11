package sptech.school.BACK_END_JAVA.agendamento.entity.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AgendamentoUpdateDto {

    @NotBlank
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}