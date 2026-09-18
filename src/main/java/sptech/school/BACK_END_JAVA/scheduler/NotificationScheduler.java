package sptech.school.BACK_END_JAVA.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.service.AgendamentoService;

import java.time.LocalDate;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Service
public class NotificationScheduler {

    private final WebClient webClient;
    private final AgendamentoService agendamentoService;

    public NotificationScheduler(WebClient webClient, AgendamentoService agendamentoService) {
        this.webClient = webClient;
        this.agendamentoService = agendamentoService;
    }


   //@Scheduled(cron = "0 0 9,12,16,20 * * *", zone = "America/Sao_Paulo")
   @Scheduled(cron = "0 * * * * *", zone = "America/Sao_Paulo")
   public void enviarNotificacao() {

       System.out.println("ENVIANDO!");
       LocalDate alvo = LocalDate.now().plusDays(1);
       List<Agendamento> agendamentos = agendamentoService.consultarPorData(alvo);

       agendamentos.stream()
           .filter(agendamento -> !"CONFIRMADO".equalsIgnoreCase(agendamento.getStatus()))
           .filter(agendamento -> !"CANCELADO".equalsIgnoreCase(agendamento.getStatus()))
           .forEach(agendamento -> {

           Map<String, Object> body = new HashMap<>();

               System.out.println("====== INFOMRAÇÔES DO AGENDAMENTO ======");
               System.out.println(" CLIENTE:" + agendamento.getCliente().getUsuario().toString());
               System.out.println(" STATUS:" + agendamento.getStatus().toString());
               System.out.println(" ORDEM NSU:" + agendamento.getOrdemPedido());
               System.out.println("=========================================");

           body.put("telefone", agendamento.getCliente().getUsuario().getTelefone());
           body.put("cliente", agendamento.getCliente().getUsuario().getNome());
           body.put("servico", agendamento.getServico().getNome());
           body.put("data", agendamento.getData().toString());
           body.put("horaInicio", agendamento.getHoraInicio().toString());
           body.put("ordemPedido", agendamento.getOrdemPedido());

           webClient.post()
                   .uri("http://localhost:8090/notify/lembrete-agendamento")
                   .contentType(MediaType.APPLICATION_JSON)
                   .bodyValue(body)
                   .retrieve()
                   .bodyToMono(String.class)
                   .subscribe(
                           response -> log.info("Lembrete enviado com sucesso: {}", response),
                           error -> log.error("Erro ao enviar lembrete: {}", error.getMessage())
                               );

           }
           );
   }
}