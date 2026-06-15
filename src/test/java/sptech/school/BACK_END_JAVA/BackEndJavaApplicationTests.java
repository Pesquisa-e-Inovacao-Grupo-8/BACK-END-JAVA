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
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BackEndJavaApplicationTests {

	//Agendamento
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
				UUID id = UUID.randomUUID();
				when(agendamentoRepository.findById(id)).thenReturn(Optional.empty());

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoService.buscarPorId(id));

				assertEquals("Agendamento não encontrado", exception.getMessage());
				verify(agendamentoRepository).findById(id);
			}

		}

		@Nested
		class criarAgendamento {

			@Test
			@DisplayName("Deve lançar exceção 'Profissional não encontrado' quando o profissional não existir")
			void deveLancarExcecaoQuandoProfissionalNaoEncontrado() {
				UUID profissionalId = UUID.randomUUID();
				UUID servicoId = UUID.randomUUID();
				Agendamento agendamento = new Agendamento();

				when(profissionalRepository.findById(profissionalId)).thenReturn(Optional.empty());

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoService.criar(agendamento, null, "Cliente Avulso", "11999999999", profissionalId, servicoId));

				assertEquals("Profissional não encontrado", exception.getMessage());
				verify(profissionalRepository).findById(profissionalId);
				verify(servicoRepository, never()).findById(any());
				verify(agendamentoRepository, never()).save(any());
			}

			@Test
			@DisplayName("Deve lançar exceção 'Serviço não encontrado' quando o serviço não existir")
			void deveLancarExcecaoQuandoServicoNaoEncontrado() {
				UUID profissionalId = UUID.randomUUID();
				UUID servicoId = UUID.randomUUID();
				Agendamento agendamento = new Agendamento();
				Profissional profissional = new Profissional();

				when(profissionalRepository.findById(profissionalId)).thenReturn(Optional.of(profissional));
				when(servicoRepository.findById(servicoId)).thenReturn(Optional.empty());

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoService.criar(agendamento, null, "Cliente Avulso", "11999999999", profissionalId, servicoId));

				assertEquals("Serviço não encontrado", exception.getMessage());
				verify(servicoRepository).findById(servicoId);
				verify(agendamentoRepository, never()).save(any());
			}

			@Test
			@DisplayName("Quando o cliente informado não for encontrado, deve tratar o agendamento como avulso em vez de lançar exceção")
			void deveTratarComoAvulsoQuandoClienteNaoEncontrado() {
				UUID clienteId = UUID.randomUUID();
				UUID profissionalId = UUID.randomUUID();
				UUID servicoId = UUID.randomUUID();

				Agendamento agendamento = new Agendamento();
				Profissional profissional = new Profissional();
				Servico servico = new Servico();
				servico.setPreco(100.0);

				when(clienteRepository.findByUsuarioId(clienteId)).thenReturn(Optional.empty());
				when(profissionalRepository.findById(profissionalId)).thenReturn(Optional.of(profissional));
				when(servicoRepository.findById(servicoId)).thenReturn(Optional.of(servico));
				when(agendamentoRepository.save(any(Agendamento.class))).thenAnswer(invocation -> invocation.getArgument(0));

				Agendamento resultado = agendamentoService.criar(agendamento, clienteId, "Cliente Avulso", "11999999999", profissionalId, servicoId);

				assertNull(resultado.getCliente());
				assertEquals("Cliente Avulso", resultado.getNomeClienteAvulso());
				assertEquals("11999999999", resultado.getTelefoneClienteAvulso());
				verify(agendamentoRepository).save(any(Agendamento.class));
			}
		}

		@Nested
		class atualizarAgendamento {

			@Test
			@DisplayName("Deve lançar exceção 'Agendamento não encontrado' quando o id não existir")
			void deveLancarExcecaoQuandoAgendamentoNaoEncontrado() {
				UUID id = UUID.randomUUID();
				Agendamento agendamento = new Agendamento();

				when(agendamentoRepository.existsById(id)).thenReturn(false);

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoService.atualizar(id, agendamento));

				assertEquals("Agendamento não encontrado", exception.getMessage());
				verify(agendamentoRepository).existsById(id);
				verify(agendamentoRepository, never()).save(any());

			}
		}

			@Nested
			class deletarAgendamento {

				@Test
				@DisplayName("Deve deletar agendamentos corretamente")
				void deveDeletarAgendamentos() {
						UUID id = UUID.randomUUID();

						when(agendamentoRepository.existsById(id)).thenReturn(false);

						RuntimeException exception = assertThrows(RuntimeException.class,
								() -> agendamentoService.deletar(id));

						assertEquals("Agendamento não encontrado", exception.getMessage());
						verify(agendamentoRepository).existsById(id);
						verify(agendamentoRepository, never()).deleteById(any());

					}

			}
		}

	//agendamentoServico
	@Nested
	class agendamentoServico {

		@Mock
		private AgendamentoServicoRepository agendamentoServicoRepository;

		@Mock
		private AgendamentoRepository agendamentoRepository;

		@Mock
		private ServicoRepository servicoRepository;

		@Mock
		private WebClient webClient;

		@InjectMocks
		private AgendamentoServicoService agendamentoServicoService;


		@Nested
		class buscarAgendamentoServico {

			@Test
			@DisplayName("Deve buscar agendamentos de servicos corretamente")
			void deveBuscarAgendamentoServico() {
				UUID id = UUID.randomUUID();
				when(agendamentoServicoRepository.findById(id)).thenReturn(Optional.empty());

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoServicoService.buscarPorId(id));

				assertEquals("AgendamentoServico não encontrado", exception.getMessage());
				verify(agendamentoServicoRepository).findById(id);
			}

		}

		@Nested
		class criarAgendamentoServico {

			@Test
			@DisplayName("Deve criar agendamento de servicos corretamente")
			void deveCriarAgendamentoServico() {

				UUID agendamentoId = UUID.randomUUID();
				UUID servicoId = UUID.randomUUID();

				when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.empty());

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoServicoService.criar(agendamentoId, servicoId));

				assertEquals("Agendamento não encontrado", exception.getMessage());
				verify(agendamentoRepository).findById(agendamentoId);
				verify(servicoRepository, never()).findById(any());
				verify(agendamentoServicoRepository, never()).save(any());

			}

		}

		@Nested
		class listarAgendamentoServico {

			@Test
			@DisplayName("Deve listar agendamento de servicos corretamente")
			void deveListarAgendamentoServico() {
				UUID agendamentoId = UUID.randomUUID();
				UUID servicoId = UUID.randomUUID();
				Agendamento agendamento = new Agendamento();

				when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.of(agendamento));
				when(servicoRepository.findById(servicoId)).thenReturn(Optional.empty());

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoServicoService.criar(agendamentoId, servicoId));

				assertEquals("Serviço não encontrado", exception.getMessage());
				verify(servicoRepository).findById(servicoId);
				verify(agendamentoServicoRepository, never()).save(any());

			}
		}

		@Nested
		class atualizarAgendamentoServico {

			@Test
			@DisplayName("Deve lançar exceção 'AgendamentoServico não encontrado' quando o id não existir")
			void deveLancarExcecaoQuandoAgendamentoServicoNaoEncontrado() {
				UUID id = UUID.randomUUID();
				UUID agendamentoId = UUID.randomUUID();
				UUID servicoId = UUID.randomUUID();

				when(agendamentoServicoRepository.existsById(id)).thenReturn(false);

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoServicoService.atualizar(id, agendamentoId, servicoId));

				assertEquals("AgendamentoServico não encontrado", exception.getMessage());
				verify(agendamentoServicoRepository).existsById(id);
				verify(agendamentoRepository, never()).findById(any());
				verify(agendamentoServicoRepository, never()).save(any());
			}


			@Test
			@DisplayName("Deve lançar exceção 'Agendamento não encontrado' quando o agendamento informado não existir")
			void deveLancarExcecaoQuandoAgendamentoNaoEncontrado() {
				UUID id = UUID.randomUUID();
				UUID agendamentoId = UUID.randomUUID();
				UUID servicoId = UUID.randomUUID();

				when(agendamentoServicoRepository.existsById(id)).thenReturn(true);
				when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.empty());

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoServicoService.atualizar(id, agendamentoId, servicoId));

				assertEquals("Agendamento não encontrado", exception.getMessage());
				verify(agendamentoRepository).findById(agendamentoId);
				verify(servicoRepository, never()).findById(any());
				verify(agendamentoServicoRepository, never()).save(any());

			}

			@Test
			@DisplayName("Deve lançar exceção 'Serviço não encontrado' quando o serviço informado não existir")
			void deveLancarExcecaoQuandoServicoNaoEncontrado() {
				UUID id = UUID.randomUUID();
				UUID agendamentoId = UUID.randomUUID();
				UUID servicoId = UUID.randomUUID();
				Agendamento agendamento = new Agendamento();

				when(agendamentoServicoRepository.existsById(id)).thenReturn(true);
				when(agendamentoRepository.findById(agendamentoId)).thenReturn(Optional.of(agendamento));
				when(servicoRepository.findById(servicoId)).thenReturn(Optional.empty());

				RuntimeException exception = assertThrows(RuntimeException.class,
						() -> agendamentoServicoService.atualizar(id, agendamentoId, servicoId));

				assertEquals("Serviço não encontrado", exception.getMessage());
				verify(servicoRepository).findById(servicoId);
				verify(agendamentoServicoRepository, never()).save(any());
			}
		}

			@Nested
			class deletarAgendamentoServico {

				@Test
				@DisplayName("Deve deletar agendamento de servicos corretamente")
				void deveDeletarAgendamentoServico() {
					UUID id = UUID.randomUUID();

					when(agendamentoServicoRepository.existsById(id)).thenReturn(false);

					RuntimeException exception = assertThrows(RuntimeException.class,
							() -> agendamentoServicoService.deletar(id));

					assertEquals("AgendamentoServico não encontrado", exception.getMessage());
					verify(agendamentoServicoRepository).existsById(id);
					verify(agendamentoServicoRepository, never()).deleteById(any());

				}
			}
		}

		//cliente
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
					UUID id = UUID.randomUUID();
					when(clienteRepository.findById(id)).thenReturn(Optional.empty());

					RuntimeException exception = assertThrows(RuntimeException.class,
							() -> clienteService.buscarPorId(id));

					assertEquals("Cliente não encontrado", exception.getMessage());
					verify(clienteRepository).findById(id);

				}
			}

			@Nested
			class criarCliente {

				@Test
				@DisplayName("Deve criar clientes corretamente")
				void deveCriarClientes() {
					UUID usuarioId = UUID.randomUUID();
					Cliente cliente = new Cliente();

					when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.empty());

					RuntimeException exception = assertThrows(RuntimeException.class,
							() -> clienteService.criar(cliente, usuarioId));

					assertEquals("Usuário não encontrado", exception.getMessage());
					verify(usuarioRepository).findById(usuarioId);
					verify(clienteRepository, never()).save(any());

				}
			}

			@Nested
			class atualizarCliente {

				@Test
				@DisplayName("Deve atualizar clientes corretamente")
				void deveAtualizarClientes() {
					UUID id = UUID.randomUUID();
					Cliente cliente = new Cliente();

					when(clienteRepository.existsById(id)).thenReturn(false);

					RuntimeException exception = assertThrows(RuntimeException.class,
							() -> clienteService.atualizar(id, cliente));

					assertEquals("Cliente não encontrado", exception.getMessage());
					verify(clienteRepository).existsById(id);
					verify(clienteRepository, never()).save(any());
				}
			}

			@Nested
			class deletarCliente {

				@Test
				@DisplayName("Deve deletar clientes corretamente")
				void deveDeletarClientes() {
					UUID id = UUID.randomUUID();

					when(clienteRepository.existsById(id)).thenReturn(false);

					RuntimeException exception = assertThrows(RuntimeException.class,
							() -> clienteService.deletar(id));

					assertEquals("Cliente não encontrado", exception.getMessage());
					verify(clienteRepository).existsById(id);
					verify(clienteRepository, never()).deleteById(any());

				}
			}

		}

		//clientePacote
		class clientePacote {

			@Nested
			class buscarClientePacote {

			}

			@Nested
			class criarClientePacote {

			}

			@Nested
			class listarClientePacote {

			}

			@Nested
			class deletarClientePacote {

			}
		}


		//pagamento
		@Nested
		class pagamento {

			@Nested
			class buscarPagamento {

			}

			@Nested
			class criarPagamento {

			}

			@Nested
			class listarPagamento {

			}

			@Nested
			class deletarPagamento {

			}

		}

		//profissional
		@Nested
		class profissional {

			@Nested
			class buscarProfissional {

			}

			@Nested
			class criarProfissional {

			}

			@Nested
			class listarProfissional {

			}

			@Nested
			class deletarProfissional {

			}

		}

		//servico
		@Nested
		class servico {

			@Nested
			class buscarServico {

			}

			@Nested
			class criarServico {

			}

			@Nested
			class listarServico {

			}

			@Nested
			class atualizarServico {

			}

			@Nested
			class deletarServico {

			}

		}

		//usuario
		@Nested
		class usuario {

			@Nested
			class buscarUsuario {

			}

			@Nested
			class criarUsuario {

			}

			@Nested
			class listarUsuario {

			}

			@Nested
			class deletarUsuario {

			}

		}
}
