package sptech.school.BACK_END_JAVA.controller;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.entity.Agendamento;
import sptech.school.BACK_END_JAVA.repository.AgendamentoRepository;
import sptech.school.BACK_END_JAVA.service.AgendamentoService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/spring/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {

    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @PostMapping("/criar")
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody Agendamento agendamento) {
        service.criarAgendamento(agendamento);
        return  ResponseEntity.status(200).body(agendamento);
    }

    @GetMapping("/buscar/{ordemAgendamento}")
    public ResponseEntity<Agendamento> buscarPorOrdemAgendamento(@PathVariable String ordemAgendamento) {
        Agendamento agendamento = service.buscarPorOrdemAgendamento(ordemAgendamento);
        return ResponseEntity.status(200).body(agendamento);
    }

    @PutMapping("/atualizar/{ordemAgendamento}")
    public ResponseEntity<Agendamento> atualizar(@PathVariable String ordemAgendamento, @RequestBody Agendamento dados) {

        Agendamento atualizado = service.atualizarAgendamento(ordemAgendamento, dados);

        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Agendamento>> listarTodos() {
        return ResponseEntity.status(200).body(service.listarTodos());
    }
//
//
//
//    @DeleteMapping("/ordem/{ordemAgendamento}")
//    public ResponseEntity<Void> deletar(@PathVariable String ordemAgendamento) {
//
//    }
}
