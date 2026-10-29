package sptech.school.BACK_END_JAVA.servico.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.entity.dto.request.ServicoRequestDto;
import sptech.school.BACK_END_JAVA.servico.service.ServicoService;
import jakarta.validation.Valid;

import java.util.UUID;

@RestController
@RequestMapping("/servicos")
public class ServicoController {
    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Servico>> getServico(
            @PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> getById(@PathVariable UUID id) {
        Servico servico = service.buscarPorId(id);
        return ResponseEntity.ok(servico);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Servico> criar(@Valid @RequestBody ServicoRequestDto dto) {
        Servico criado = service.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Servico> atualizar(@PathVariable UUID id, @Valid @RequestBody ServicoRequestDto dto) {
        Servico atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //funções
}
