package sptech.school.BACK_END_JAVA.servicoProduto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.servicoProduto.entity.ServicoProduto;
import sptech.school.BACK_END_JAVA.servicoProduto.entity.dto.request.ServicoProdutoRequestDto;
import sptech.school.BACK_END_JAVA.servicoProduto.service.ServicoProdutoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/servico-produtos")
public class ServicoProdutoController {

    private final ServicoProdutoService service;

    public ServicoProdutoController(ServicoProdutoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ServicoProduto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/servico/{servicoId}")
    public ResponseEntity<List<ServicoProduto>> listarPorServico(@PathVariable UUID servicoId) {
        return ResponseEntity.ok(service.listarPorServico(servicoId));
    }

    @PostMapping
    public ResponseEntity<ServicoProduto> vincularProdutoAServico(@RequestBody ServicoProdutoRequestDto dto) {
        ServicoProduto criado = service.criar(dto.getServicoId(), dto.getProdutoId(), dto.getQuantidadeUsada());
        return ResponseEntity.status(201).body(criado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

