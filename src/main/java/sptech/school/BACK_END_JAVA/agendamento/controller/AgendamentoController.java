package sptech.school.BACK_END_JAVA.agendamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoRequestDto;
import sptech.school.BACK_END_JAVA.agendamento.service.AgendamentoService;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendamentos")
public class AgendamentoController {
    private final AgendamentoService service;
    private final UsuarioRepository usuarioRepository;

    public AgendamentoController(AgendamentoService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<Agendamento>> getAgendamento() {
        List<Agendamento> agendamentos = service.listar();
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> getAgendamentoById(@PathVariable UUID id) {
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
    public ResponseEntity<Agendamento> atualizarAgendamento(@PathVariable UUID id, @RequestBody Agendamento agendamento) {

        Agendamento atualizado = service.atualizar(id, agendamento);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //restante das funções

}
