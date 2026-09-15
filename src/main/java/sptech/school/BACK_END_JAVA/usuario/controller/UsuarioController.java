package sptech.school.BACK_END_JAVA.usuario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;
import sptech.school.BACK_END_JAVA.usuario.entity.dto.request.UsuarioCreateRequestDto;
import sptech.school.BACK_END_JAVA.usuario.entity.dto.request.UsuarioUpdateRequestDto;
import sptech.school.BACK_END_JAVA.usuario.service.UsuarioService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@PreAuthorize("hasAnyRole('PROFISSIONAL', 'ADMIN')")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuario() {
        List<Usuario> usuarios = service.listar();
        return ResponseEntity.ok(usuarios);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable UUID id) {
        Usuario usuario = service.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody UsuarioCreateRequestDto dto) {
        Usuario criado = service.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable UUID id, @Valid @RequestBody UsuarioUpdateRequestDto dto) {
        Usuario atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //funções
}
