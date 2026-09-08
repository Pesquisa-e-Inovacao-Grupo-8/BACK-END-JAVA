package sptech.school.BACK_END_JAVA.agendamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoRequestDto;
import sptech.school.BACK_END_JAVA.agendamento.service.AgendamentoService;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/agendamentos")
@PreAuthorize("hasAnyRole('CLIENTE', 'PROFISSIONAL', 'ADMIN')")
public class AgendamentoController {
    private final AgendamentoService service;
    private final UsuarioRepository usuarioRepository;

    public AgendamentoController(AgendamentoService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> getAgendamento(Authentication authentication) {
        List<Agendamento> agendamentos = service.listar(authentication);
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<HorarioOcupadoResponse>> disponibilidade(
            @RequestParam UUID profissionalId,
            @RequestParam LocalDate data) {
        return ResponseEntity.ok(service.listarDisponibilidade(profissionalId, data).stream()
                .map(agendamento -> new HorarioOcupadoResponse(
                        agendamento.getHoraInicio(),
                        agendamento.getHoraFim()))
                .toList());
    }

    public record HorarioOcupadoResponse(LocalTime horaInicio, LocalTime horaFim) {}

    @GetMapping("/horarios-disponiveis")
    public ResponseEntity<List<String>> horariosDisponiveis(
            @RequestParam UUID profissionalId,
            @RequestParam UUID servicoId,
            @RequestParam LocalDate data) {
        return ResponseEntity.ok(service.listarHorariosDisponiveis(profissionalId, servicoId, data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> getAgendamentoById(@PathVariable UUID id, Authentication authentication) {
        if (!service.podeAcessar(id, authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Agendamento agendamento = service.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PostMapping
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody AgendamentoRequestDto dto, Authentication authentication) {
        // If user is authenticated, set usuarioId in the DTO from the authenticated user
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            usuarioRepository.findByEmail(authentication.getName())
                    .ifPresent(usuario -> dto.setUsuarioId(usuario.getId()));
        }

        Agendamento agendamentoCriado = service.criar(dto);
        return ResponseEntity.status(201).body(agendamentoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> atualizarAgendamento(@PathVariable UUID id, @RequestBody Agendamento agendamento, Authentication authentication) {
        if (!service.podeAcessar(id, authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        Agendamento atualizado = service.atualizar(id, agendamento);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable UUID id, Authentication authentication) {
        if (!service.podeAcessar(id, authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //restante das funções

}
