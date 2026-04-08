package sptech.school.BACK_END_JAVA.servicoProfissional.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.servicoProfissional.entity.ServicoProfissional;
import sptech.school.BACK_END_JAVA.servicoProfissional.service.ServicoProfissionalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/servicoProfissionais")
public class ServicoProfissionalController {
    private final ServicoProfissionalService service;

    public ServicoProfissionalController(ServicoProfissionalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ServicoProfissional>> getServicoProfissional() {
        List<ServicoProfissional> servicoProfissionais = service.listar();
        return ResponseEntity.ok(servicoProfissionais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoProfissional> getById(@PathVariable UUID id) {
        ServicoProfissional sp = service.buscarPorId(id);
        return ResponseEntity.ok(sp);
    }

    @PostMapping
    public ResponseEntity<ServicoProfissional> criar(@RequestBody ServicoProfissional sp, @RequestParam UUID servicoId, @RequestParam UUID profissionalId) {
        ServicoProfissional criado = service.criar(sp, servicoId, profissionalId);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoProfissional> atualizar(@PathVariable UUID id, @RequestBody ServicoProfissional sp) {
        ServicoProfissional atualizado = service.atualizar(id, sp);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    //funções
}
