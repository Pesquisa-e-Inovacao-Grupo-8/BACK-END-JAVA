package sptech.school.BACK_END_JAVA.agendamento.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoRequestDto;
import sptech.school.BACK_END_JAVA.agendamento.repository.AgendamentoRepository;
import sptech.school.BACK_END_JAVA.agendamento.strategy.AgendamentoStrategy;
import sptech.school.BACK_END_JAVA.agendamento.strategy.AgendamentoStrategyFactory;
import sptech.school.BACK_END_JAVA.agendamentoServico.entity.AgendamentoServico;
import sptech.school.BACK_END_JAVA.agendamentoServico.repository.AgendamentoServicoRepository;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.time.LocalTime;
import java.time.LocalDate;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import sptech.school.BACK_END_JAVA.profissionalHorario.entity.ProfissionalHorario;
import sptech.school.BACK_END_JAVA.profissionalHorario.repository.ProfissionalHorarioRepository;
import sptech.school.BACK_END_JAVA.servicoProfissional.repository.ServicoProfissionalRepository;

@Service
public class AgendamentoService {
    private static final Logger logger = LoggerFactory.getLogger(AgendamentoService.class);

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProfissionalRepository profissionalRepository;
    private final ServicoRepository servicoRepository;
    private final AgendamentoServicoRepository agendamentoServicoRepository;
    private final AgendamentoStrategyFactory factory;
    private final ProfissionalHorarioRepository profissionalHorarioRepository;
    private final ServicoProfissionalRepository servicoProfissionalRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, ClienteRepository clienteRepository, UsuarioRepository usuarioRepository, ProfissionalRepository profissionalRepository, ServicoRepository servicoRepository, AgendamentoServicoRepository agendamentoServicoRepository, AgendamentoStrategyFactory factory, ProfissionalHorarioRepository profissionalHorarioRepository, ServicoProfissionalRepository servicoProfissionalRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.profissionalRepository = profissionalRepository;
        this.servicoRepository = servicoRepository;
        this.agendamentoServicoRepository = agendamentoServicoRepository;
        this.factory = factory;
        this.profissionalHorarioRepository = profissionalHorarioRepository;
        this.servicoProfissionalRepository = servicoProfissionalRepository;
    }

    public List<Agendamento> listar(Authentication authentication) {
        if (temRole(authentication, "ROLE_ADMIN")) return agendamentoRepository.findAll();
        if (temRole(authentication, "ROLE_PROFISSIONAL")) {
            return agendamentoRepository.findByProfissional_Usuario_Email(authentication.getName());
        }
        return agendamentoRepository.findByCliente_Usuario_Email(authentication.getName());
    }

    public List<Agendamento> listarDisponibilidade(UUID profissionalId, LocalDate data) {
        return agendamentoRepository.findByProfissional_IdAndDataOrderByHoraInicio(profissionalId, data).stream()
                .filter(agendamento -> !"CANCELADO".equalsIgnoreCase(agendamento.getStatus()))
                .toList();
    }

            public List<String> listarHorariosDisponiveis(UUID profissionalId, UUID servicoId, LocalDate data) {
            profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profissional não encontrado"));
            Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado"));

            if (!Boolean.TRUE.equals(servico.getAtivo())
                || !servicoProfissionalRepository.existsByProfissional_IdAndServico_Id(profissionalId, servicoId)) {
                return List.of();
            }

            ProfissionalHorario horario = profissionalHorarioRepository
                .findByProfissional_IdAndDiaSemana(profissionalId, data.getDayOfWeek().getValue())
                .orElse(null);
            if (horario == null || !Boolean.TRUE.equals(horario.getAtivo())) return List.of();

            int duracao = servico.getDuracaoMinutos();
            int intervalo = horario.getIntervaloMinutos() == null ? 0 : horario.getIntervaloMinutos();
            List<Agendamento> ocupados = listarDisponibilidade(profissionalId, data);
            List<String> disponiveis = new java.util.ArrayList<>();

            for (LocalTime inicio = arredondarCinco(horario.getHoraInicio());
                 !inicio.plusMinutes(duracao).isAfter(horario.getHoraFim());
                 inicio = arredondarCinco(inicio.plusMinutes(duracao + intervalo))) {
                LocalTime inicioAtual = inicio;
                LocalTime fim = inicioAtual.plusMinutes(duracao);
                LocalTime fimBloqueado = fim.plusMinutes(intervalo);
                boolean conflito = ocupados.stream().anyMatch(ocupado ->
                    inicioAtual.isBefore(ocupado.getHoraFim().plusMinutes(intervalo))
                        && fimBloqueado.isAfter(ocupado.getHoraInicio()));
                if (!conflito) disponiveis.add(inicioAtual.toString().substring(0, 5));
            }
            return disponiveis.stream().distinct().toList();
            }

            private LocalTime arredondarCinco(LocalTime hora) {
            int minuto = hora.getMinute();
            int arredondado = ((minuto + 4) / 5) * 5;
            return hora.withMinute(0).withSecond(0).withNano(0).plusMinutes(arredondado);
            }

    public boolean podeAcessar(UUID id, Authentication authentication) {
        if (temRole(authentication, "ROLE_ADMIN")) return true;
        Agendamento agendamento = buscarPorId(id);
        if (temRole(authentication, "ROLE_PROFISSIONAL")) {
            return agendamento.getProfissional() != null
                    && agendamento.getProfissional().getUsuario() != null
                    && authentication.getName().equals(agendamento.getProfissional().getUsuario().getEmail());
        }
        return agendamento.getCliente() != null
                && agendamento.getCliente().getUsuario() != null
                && authentication.getName().equals(agendamento.getCliente().getUsuario().getEmail());
    }

    private boolean temRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> role.equals(authority.getAuthority()));
    }

    public Agendamento buscarPorId(UUID id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }

    @Transactional
    public Agendamento criar(AgendamentoRequestDto dto) {

        if (dto.getData() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe a data do agendamento");
        }

        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        if ((dto.getServicos() == null || dto.getServicos().isEmpty())
                && dto.getServicoId() != null) {
            dto.setServicos(List.of(dto.getServicoId()));
        }

        if (dto.getServicos() == null || dto.getServicos().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe ao menos um serviço");
        }

        LocalTime horaInicio = validarHora(dto.getHoraInicio());
        List<Servico> servicos = dto.getServicos().stream()
            .distinct()
            .map(servicoId -> servicoRepository.findById(servicoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Serviço não encontrado: " + servicoId)))
            .toList();

        if (servicos.stream().anyMatch(servico -> !Boolean.TRUE.equals(servico.getAtivo()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O serviço selecionado está inativo");
        }

        if (servicos.stream().anyMatch(servico -> !servicoProfissionalRepository
            .existsByProfissional_IdAndServico_Id(profissional.getId(), servico.getId()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "O serviço não está vinculado à profissional selecionada");
        }

        int duracaoTotal = servicos.stream()
            .mapToInt(servico -> servico.getDuracaoMinutos())
            .sum();
        int diaSemana = dto.getData().getDayOfWeek().getValue();
        ProfissionalHorario horario = profissionalHorarioRepository
            .findByProfissional_IdAndDiaSemana(profissional.getId(), diaSemana)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "A profissional não possui horário configurado para este dia"));

        if (!Boolean.TRUE.equals(horario.getAtivo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A profissional não atende neste dia");
        }

        LocalTime horaFim = horaInicio.plusMinutes(duracaoTotal);
        if (horaInicio.isBefore(horario.getHoraInicio()) || horaFim.isAfter(horario.getHoraFim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "O agendamento está fora do horário de atendimento");
        }

        List<Agendamento> existentes = agendamentoRepository
            .findByProfissional_IdAndData(profissional.getId(), dto.getData());
        int intervalo = horario.getIntervaloMinutos() == null ? 0 : horario.getIntervaloMinutos();
        boolean conflito = existentes.stream()
            .filter(agendamento -> !"CANCELADO".equalsIgnoreCase(
                agendamento.getStatus() == null ? "" : agendamento.getStatus().trim()))
            .anyMatch(agendamento -> {
                LocalTime inicioExistente = agendamento.getHoraInicio();
                LocalTime fimExistente = agendamento.getHoraFim();
                if (inicioExistente == null || fimExistente == null) return true;

                // O atendimento existente bloqueia também o intervalo posterior.
                LocalTime fimBloqueado = fimExistente.plusMinutes(intervalo);
                return horaInicio.isBefore(fimBloqueado)
                    && horaFim.plusMinutes(intervalo).isAfter(inicioExistente);
            });
        if (conflito) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "O horário escolhido conflita com outro agendamento ou intervalo");
        }

        tentarVincularClienteCadastrado(dto);

        garantirClienteDoUsuario(dto);

        logger.info("usuarioId recebido: {}", dto.getUsuarioId());
        logger.info("clienteId após vinculação: {}", dto.getClienteId());

        Agendamento agendamento = new Agendamento();

        agendamento.setData(dto.getData());
        agendamento.setHoraInicio(horaInicio);
        agendamento.setHoraFim(horaFim);
        agendamento.setServico(servicos.get(0));
        agendamento.setStatus(dto.getStatus());
        agendamento.setProfissional(profissional);
        agendamento.setOrdemPedido(UUID.randomUUID().toString());
        agendamento.setValorTotal(0.0);

        AgendamentoStrategy strategy = factory.escolher(dto);
        strategy.aplicar(agendamento, dto);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        Double valorTotal = 0.0;
        for (Servico servico : servicos) {

            valorTotal += servico.getPreco();

            AgendamentoServico agendamentoServico = new AgendamentoServico();
            agendamentoServico.setAgendamento(agendamentoSalvo);
            agendamentoServico.setServico(servico);

            agendamentoServicoRepository.save(agendamentoServico);
        }

        agendamentoSalvo.setValorTotal(valorTotal);
        agendamentoRepository.save(agendamentoSalvo);

        return agendamentoSalvo;
    }

    private LocalTime validarHora(LocalTime horaInicio) {
        if (horaInicio == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe o horário inicial");
        }
        return horaInicio;
    }

    public Agendamento atualizar(UUID id, Agendamento agendamento) {

        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado");
        }

        agendamento.setId(id);
        return agendamentoRepository.save(agendamento);
    }

    public void deletar(UUID id) {

        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado");
        }

        agendamentoRepository.deleteById(id);
    }

    private void tentarVincularClienteCadastrado(AgendamentoRequestDto dto) {




            // Cliente já informado pelo frontend
            if (dto.getClienteId() != null
                    && clienteRepository.findById(dto.getClienteId()).isPresent()) {
                return;
            }

            // O frontend pode enviar o id_usuario no campo clienteId.
            // Limpa esse valor para resolver corretamente pelo usuarioId abaixo.
            dto.setClienteId(null);

            // Tenta encontrar o cliente através do usuário
            if (dto.getUsuarioId() != null) {

                clienteRepository.findByUsuario_Id(dto.getUsuarioId())
                        .ifPresent(cliente -> dto.setClienteId(cliente.getId()));

                if (dto.getClienteId() != null) {
                    return;
                }
            }

            // Tenta encontrar pelo telefone
            if (dto.getTelefoneClienteAvulso() != null &&
                    !dto.getTelefoneClienteAvulso().isBlank()) {

                String telefone = dto.getTelefoneClienteAvulso().trim();

                clienteRepository.findByUsuario_Telefone(telefone)
                        .ifPresent(cliente -> dto.setClienteId(cliente.getId()));
        }
    }

    private void garantirClienteDoUsuario(AgendamentoRequestDto dto) {
        if (dto.getUsuarioId() == null || dto.getClienteId() != null) return;

        clienteRepository.findByUsuario_Id(dto.getUsuarioId()).ifPresentOrElse(
                cliente -> dto.setClienteId(cliente.getId()),
                () -> usuarioRepository.findById(dto.getUsuarioId()).ifPresent(usuario -> {
                    Cliente cliente = new Cliente();
                    cliente.setUsuario(usuario);
                    dto.setClienteId(clienteRepository.save(cliente).getId());
                })
        );
    }

    public void notificarAgendamento(Agendamento agendamento) {

        // Implementação da notificação, por exemplo, chamando o endpoint do serviço de notificações
        // Pode ser feito usando WebClient ou outro mecanismo de sua escolha

        WebClient.create()
                .post()
                .uri("http://api-twillio:8090/notify/agendamento")
                .contentType(MediaType.APPLICATION_JSON) 
                .bodyValue(Map.of(
                        "telefone", agendamento.getCliente().getUsuario().getTelefone(),
                        "cliente", agendamento.getCliente().getUsuario().getNome(),
                        "servico", agendamento.getServico().getNome(),
                        "data", agendamento.getData().toString(),
                        "horaInicio", agendamento.getHoraInicio().toString(),
                        "ordemPedido", agendamento.getOrdemPedido()
                ))
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();

    }

    public List<Agendamento> consultarPorData(LocalDate dataAlvo) {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        return agendamentos.stream()
                .filter(agendamento -> agendamento.getData().equals(dataAlvo))
                .toList();
    }




    //restante das funções
}
