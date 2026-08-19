package sptech.school.BACK_END_JAVA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.repository.AgendamentoRepository;
import sptech.school.BACK_END_JAVA.agendamento.service.AgendamentoService;
import sptech.school.BACK_END_JAVA.agendamentoServico.repository.AgendamentoServicoRepository;
import sptech.school.BACK_END_JAVA.agendamentoServico.service.AgendamentoServicoService;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;
import sptech.school.BACK_END_JAVA.cliente.service.ClienteService;
import sptech.school.BACK_END_JAVA.clientePacote.repository.ClientePacoteRepository;
import sptech.school.BACK_END_JAVA.clientePacote.service.ClientePacoteService;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BackEndJavaApplicationTests {

	@Nested
	class agendamento {

		@Mock
		private AgendamentoRepository agendamentoRepository;

		@Mock
		private ClienteRepository clienteRepository;

		@Mock
		private ProfissionalRepository profissionalRepository;

		@Mock
		private ServicoRepository servicoRepository;

		@InjectMocks
		private AgendamentoService agendamentoService;


		@Nested
		class buscarAgendamento {

			@Test
			@DisplayName("Deve buscar agendamentos corretamente")
			void deveBuscarAgendamentos() {
				Integer id = 1;
				when(agendamentoRepository.findById(id)).thenReturn(Optional.empty());

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoService.buscarPorId(id));

				assertEquals("Agendamento não encontrado", exception.getMessage());
				verify(agendamentoRepository).findById(id);
			}

		}
	}

	@Nested
	class cliente {

		@Mock
		private ClienteRepository clienteRepository;

		@Mock
		private UsuarioRepository usuarioRepository;

		@InjectMocks
		private ClienteService clienteService;


		@Nested
		class buscarCliente {

			@Test
			@DisplayName("Deve buscar clientes corretamente")
			void deveBuscarClientes() {
				Integer id = 1;
				when(clienteRepository.findById(id)).thenReturn(Optional.empty());

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> clienteService.buscarPorId(id));

				assertEquals("Cliente não encontrado", exception.getMessage());
				verify(clienteRepository).findById(id);

			}
		}
	}
}