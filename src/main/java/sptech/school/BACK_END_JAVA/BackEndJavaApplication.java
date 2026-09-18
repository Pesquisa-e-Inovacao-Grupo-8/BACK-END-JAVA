package sptech.school.BACK_END_JAVA;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamentoServico.entity.AgendamentoServico;
import sptech.school.BACK_END_JAVA.agendamentoServico.service.AgendamentoServicoService;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;

import java.util.UUID;

@SpringBootApplication
@EnableScheduling
public class BackEndJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndJavaApplication.class, args);
	}


		//TESTE FORÇADO PARA DISPARAR NOTIFICAÇÃO DE CONFIRMAÇÃO DE AGENDAMENTO
	
		@Bean
		public CommandLineRunner dispararTesteInterno(AgendamentoServicoService agendamentoServicoService) {
			return args -> {
				System.out.println("========================================================================================== SPRING INICIADO COM SUCESSO! ==========================================================================================");
			};
		}



}
