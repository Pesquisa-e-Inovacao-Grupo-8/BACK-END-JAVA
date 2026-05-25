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


	// TESTE FORÇADO PARA DISPARAR NOTIFICAÇÃO DE CONFIRMAÇÃO DE AGENDAMENTO
	//
	//	@Bean
	//	public CommandLineRunner dispararTesteInterno(AgendamentoServicoService agendamentoServicoService) {
	//		return args -> {
	//			System.out.println("\n🔍 INICIANDO DISPARO DE TESTE COM WEBCLIENT...");
	//
	//			// ID do relacionamento de teste que inserimos via SQL
	//			UUID idRelacionamentoTeste = UUID.fromString("723e4567-e89b-12d3-a456-426614174001");
	//
	//			try {
	//				// 1. Busca o relacionamento que contém o Agendamento e o Serviço juntos
	//				AgendamentoServico vinculo = agendamentoServicoService.buscarPorId(idRelacionamentoTeste);
	//
	//				if (vinculo != null) {
	//					// 2. Extrai os objetos que a sua função exige como parâmetro
	//					Agendamento agendamento = vinculo.getAgendamento();
	//					Servico servico = vinculo.getServico();
	//
	//					System.out.println("✅ Dados recuperados. Chamando a função de envio...");
	//
	//					// 3. Chama a sua função exatamente como você a escreveu
	//					// (Nota: Se a função estiver dentro do seu service, use: agendamentoServicoService.enviarConfirmacaoAgendamento(...))
	//					agendamentoServicoService.enviarConfirmacaoAgendamento(agendamento, servico);
	//
	//					System.out.println("🚀 [Java] Requisição enviada de forma assíncrona pelo WebClient!");
	//				}
	//			} catch (Exception e) {
	//				System.err.println("❌ Erro ao executar teste interno: " + e.getMessage());
	//			}
	//		};
	//	}



}
