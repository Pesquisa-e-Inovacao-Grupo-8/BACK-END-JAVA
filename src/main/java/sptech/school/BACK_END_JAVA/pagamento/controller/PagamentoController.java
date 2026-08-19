package sptech.school.BACK_END_JAVA.pagamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.pagamento.entity.Pagamento;
import sptech.school.BACK_END_JAVA.pagamento.service.PagamentoService;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Pagamento>> getPagamento() {
        List<Pagamento> pagamentos = service.listar();
        return ResponseEntity.ok(pagamentos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getById(@PathVariable Integer id) {
        Pagamento pagamento = service.buscarPorId(id);
        return ResponseEntity.ok(pagamento);
    }

    @PostMapping
    public ResponseEntity<Pagamento> criar(@RequestBody Pagamento pagamento, @RequestParam Integer agendamentoId) {
        Pagamento criado = service.criar(pagamento, agendamentoId);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pagamento> atualizar(@PathVariable Integer id, @RequestBody Pagamento pagamento) {
        Pagamento atualizado = service.atualizar(id, pagamento);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}


