package sptech.school.BACK_END_JAVA.profissionalHorario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.profissional.service.ProfissionalService;
import sptech.school.BACK_END_JAVA.profissionalHorario.entity.ProfissionalHorario;
import sptech.school.BACK_END_JAVA.profissionalHorario.repository.ProfissionalHorarioRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalHorarioController {
    private final ProfissionalRepository profissionalRepository;
    private final ProfissionalHorarioRepository horarioRepository;
    private final ProfissionalService profissionalService;

    public ProfissionalHorarioController(
            ProfissionalRepository profissionalRepository,
            ProfissionalHorarioRepository horarioRepository,
            ProfissionalService profissionalService) {
        this.profissionalRepository = profissionalRepository;
        this.horarioRepository = horarioRepository;
        this.profissionalService = profissionalService;
    }

    @GetMapping("/{profissionalId}/horarios")
    public ResponseEntity<List<ProfissionalHorario>> listar(@PathVariable UUID profissionalId) {
        return ResponseEntity.ok(horarioRepository.findByProfissional_IdOrderByDiaSemana(profissionalId));
    }

    @GetMapping("/me/horarios")
    @PreAuthorize("hasRole('PROFISSIONAL')")
    public ResponseEntity<List<ProfissionalHorario>> listarMeus(Authentication authentication) {
        Profissional profissional = profissionalService.buscarOuCriarPorEmail(authentication.getName());
        return ResponseEntity.ok(horarioRepository.findByProfissional_IdOrderByDiaSemana(profissional.getId()));
    }

    @PutMapping("/me/horarios")
    @PreAuthorize("hasRole('PROFISSIONAL')")
    @Transactional
    public ResponseEntity<List<ProfissionalHorario>> salvarMeus(
            @RequestBody List<ProfissionalHorario> horarios,
            Authentication authentication) {
        Profissional profissional = profissionalService.buscarOuCriarPorEmail(authentication.getName());
        return ResponseEntity.ok(salvar(profissional, horarios));
    }

    @PutMapping("/{profissionalId}/horarios")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public ResponseEntity<List<ProfissionalHorario>> salvarComoAdmin(
            @PathVariable UUID profissionalId,
            @RequestBody List<ProfissionalHorario> horarios) {
        return profissionalRepository.findById(profissionalId)
                .map(profissional -> ResponseEntity.ok(salvar(profissional, horarios)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private List<ProfissionalHorario> salvar(Profissional profissional, List<ProfissionalHorario> recebidos) {
        if (recebidos == null || recebidos.size() != 7) {
            throw new IllegalArgumentException("Informe exatamente um horário para cada dia da semana");
        }

        Set<Integer> dias = recebidos.stream()
                .map(ProfissionalHorario::getDiaSemana)
                .collect(Collectors.toSet());
        if (dias.size() != 7 || !dias.equals(Set.of(1, 2, 3, 4, 5, 6, 7))) {
            throw new IllegalArgumentException("Informe exatamente os dias da semana de 1 a 7");
        }

        recebidos.forEach(this::validar);

        List<ProfissionalHorario> atuais = horarioRepository
            .findByProfissional_IdOrderByDiaSemana(profissional.getId());
        horarioRepository.deleteAllInBatch(atuais);
        horarioRepository.flush();

        return horarioRepository.saveAll(recebidos.stream().map(recebido -> {
            recebido.setId(null);
            recebido.setProfissional(profissional);
            if (recebido.getIntervaloMinutos() == null) recebido.setIntervaloMinutos(0);
            if (recebido.getAtivo() == null) recebido.setAtivo(false);
            return recebido;
        }).toList());
    }

    private void validar(ProfissionalHorario horario) {
        if (horario.getDiaSemana() == null || horario.getDiaSemana() < 1 || horario.getDiaSemana() > 7) {
            throw new IllegalArgumentException("Dia da semana inválido");
        }
        if (horario.getIntervaloMinutos() == null || horario.getIntervaloMinutos() < 0 || horario.getIntervaloMinutos() > 240) {
            throw new IllegalArgumentException("Intervalo deve estar entre 0 e 240 minutos");
        }
        if (Boolean.TRUE.equals(horario.getAtivo())
                && (horario.getHoraInicio() == null || horario.getHoraFim() == null
                || !horario.getHoraInicio().isBefore(horario.getHoraFim()))) {
            throw new IllegalArgumentException("O início deve ser anterior ao fim");
        }
        if (!Boolean.TRUE.equals(horario.getAtivo())) {
            horario.setHoraInicio(horario.getHoraInicio() == null ? LocalTime.MIDNIGHT : horario.getHoraInicio());
            horario.setHoraFim(horario.getHoraFim() == null ? LocalTime.MIDNIGHT : horario.getHoraFim());
        }
    }
}
