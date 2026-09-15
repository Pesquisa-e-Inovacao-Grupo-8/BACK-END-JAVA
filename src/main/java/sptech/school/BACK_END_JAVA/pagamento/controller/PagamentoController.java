package sptech.school.BACK_END_JAVA.pagamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.pagamento.entity.Pagamento;
import sptech.school.BACK_END_JAVA.pagamento.entity.dto.request.PagamentoRequestDto;
import sptech.school.BACK_END_JAVA.pagamento.entity.dto.request.PagamentoUpdateDto;
import sptech.school.BACK_END_JAVA.pagamento.service.PagamentoService;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<List<Pagamento>> getPagamento() {
        List<Pagamento> pagamentos = service.listar();
        return ResponseEntity.ok(pagamentos);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<Pagamento> getById(@PathVariable UUID id) {
        Pagamento pagamento = service.buscarPorId(id);
        return ResponseEntity.ok(pagamento);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<Pagamento> criar(@Valid @RequestBody PagamentoRequestDto dto, @RequestParam UUID agendamentoId) {
        Pagamento criado = service.criar(dto, agendamentoId);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<Pagamento> atualizar(@PathVariable UUID id, @Valid @RequestBody PagamentoUpdateDto dto) {
        Pagamento atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
