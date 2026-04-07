package sptech.school.BACK_END_JAVA.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AgendamentoOrdemJaExisteException extends RuntimeException {
    public AgendamentoOrdemJaExisteException(String message) {
        super(message);
    }
}
