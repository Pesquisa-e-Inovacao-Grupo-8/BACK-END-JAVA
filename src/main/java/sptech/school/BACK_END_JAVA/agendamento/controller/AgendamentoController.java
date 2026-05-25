package sptech.school.BACK_END_JAVA.agendamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.entity.dto.request.AgendamentoRequestDto;
import sptech.school.BACK_END_JAVA.agendamento.service.AgendamentoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendamentos")
@CrossOrigin(origins = "*")
public class AgendamentoController {
    private final AgendamentoService service;

    public AgendamentoController(AgendamentoService service) {
        this.service = service;
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
    public ResponseEntity<Agendamento> criarAgendamento(@RequestBody AgendamentoRequestDto dto) {

        Agendamento agendamentoParaCriar = new Agendamento();

        agendamentoParaCriar.setData(dto.getData());
        agendamentoParaCriar.setHoraInicio(dto.getHoraInicio());
        agendamentoParaCriar.setHoraFim(dto.getHoraFim());
        agendamentoParaCriar.setStatus(dto.getStatus());
        agendamentoParaCriar.setOrdemPedido(dto.getOrdemPedido());

        Agendamento agendamentoCriado = service.criar(
                agendamentoParaCriar,
                dto.getClienteId(),
                dto.getProfissionalId(),
                dto.getServicoId()
        );

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
