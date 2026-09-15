package sptech.school.BACK_END_JAVA.agendamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoRequestDto;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoUpdateDto;
import sptech.school.BACK_END_JAVA.agendamento.service.AgendamentoService;
import sptech.school.BACK_END_JAVA.agendamento.service.PaymentLinkService;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/agendamentos")
@PreAuthorize("hasAnyRole('CLIENTE', 'PROFISSIONAL', 'ADMIN' , 'PAYMENT')")
public class AgendamentoController {
    private final AgendamentoService service;
    private final PaymentLinkService paymentLinkService;
    private final UsuarioRepository usuarioRepository;

    public AgendamentoController(AgendamentoService service, PaymentLinkService paymentLinkService, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.paymentLinkService = paymentLinkService;
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
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROFISSIONAL', 'ADMIN', 'PAYMENT')")
    public ResponseEntity<Agendamento> getAgendamentoById(@PathVariable UUID id, Authentication authentication) {
        if (!service.podeAcessar(id, authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        Agendamento agendamento = service.buscarPorId(id);
        return ResponseEntity.ok(agendamento);
    }

    @PostMapping("/{id}/payment-link")
    @PreAuthorize("hasAnyRole('CLIENTE', 'PROFISSIONAL', 'ADMIN')")
    public ResponseEntity<Map<String, String>> gerarLinkPagamento(
            @PathVariable UUID id,
            Authentication authentication) {
        if (!service.podeAcessar(id, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String link = paymentLinkService.gerarOuRecuperar(id);
        return ResponseEntity.ok(Map.of("url", link));
    }

    @PostMapping
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody AgendamentoRequestDto dto, Authentication authentication) {
        // If user is authenticated, set usuarioId in the DTO from the authenticated user
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            usuarioRepository.findByEmail(authentication.getName())
                    .ifPresent(usuario -> dto.setUsuarioId(usuario.getId()));
        }

        Agendamento agendamentoCriado = service.criar(dto);
        service.notificarAgendamento(agendamentoCriado);

        return ResponseEntity.status(201).body(agendamentoCriado);
    }

    @PutMapping("/{ordemPedido}")
    public ResponseEntity<Agendamento> atualizarAgendamento(
            @PathVariable String ordemPedido,
            @Valid @RequestBody AgendamentoUpdateDto dto,
            Authentication authentication) {
        Agendamento agendamento = service.buscarPorReferencia(ordemPedido);
        if (!service.podeAcessar(agendamento.getId(), authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Agendamento atualizado = service.atualizar(agendamento.getId(), dto);
        return ResponseEntity.ok(atualizado);
    }

    @PatchMapping("/{id}/pagamento")
    @PreAuthorize("hasRole('PAYMENT')")
    public ResponseEntity<Agendamento> atualizarPagamentoPorWebhook(
            @PathVariable UUID id,
            @Valid @RequestBody AgendamentoUpdateDto dto) {
        Agendamento atualizado = service.atualizar(id, dto);
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
