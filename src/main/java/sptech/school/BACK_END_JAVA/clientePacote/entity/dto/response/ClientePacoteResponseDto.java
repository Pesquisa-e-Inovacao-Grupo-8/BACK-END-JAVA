package sptech.school.BACK_END_JAVA.clientePacote.entity.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ClientePacoteResponseDto(
        UUID id,
        String nome,
        String descricao,
        Boolean ativo,
        LocalDateTime dtExpiracao,
        List<ServicoPacoteResponseDto> servicos) {

    public record ServicoPacoteResponseDto(
            UUID id,
            String nome,
            Integer duracaoMinutos,
            Integer quantidadeDisponivel,
            Integer quantidadeConfigurada,
            UUID clientePacoteServicoId) {
    }
}
