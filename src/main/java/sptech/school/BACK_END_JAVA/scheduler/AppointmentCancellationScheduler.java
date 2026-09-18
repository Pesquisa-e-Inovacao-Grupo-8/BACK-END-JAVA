package sptech.school.BACK_END_JAVA.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.repository.AgendamentoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentCancellationScheduler {

    private final AgendamentoRepository agendamentoRepository;

    public AppointmentCancellationScheduler(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    @Scheduled(
            cron = "0 59 23 * * *",
            zone = "${app.scheduler.zone:America/Sao_Paulo}")
    @Transactional
    public void cancelarAgendamentosNaoConfirmados() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        List<Agendamento> agendamentos = agendamentoRepository.findByData(amanha);

        int cancelados = 0;
        for (Agendamento agendamento : agendamentos) {
            String status = agendamento.getStatus();
            if (!"CONFIRMADO".equalsIgnoreCase(status)
                    && !"CANCELADO".equalsIgnoreCase(status)) {
                agendamento.setStatus("CANCELADO");
                cancelados++;
            }
        }

        if (cancelados > 0) {
            agendamentoRepository.saveAll(agendamentos);
        }

        System.out.printf(
                "Scheduler de cancelamento: %d agendamento(s) cancelado(s) para %s.%n",
                cancelados,
                amanha);
    }
}