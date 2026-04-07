package sptech.school.BACK_END_JAVA.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.service.FuncionariaService;
import sptech.school.BACK_END_JAVA.entity.Funcionaria;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/spring/funcionarias")
@CrossOrigin(origins = "*")
public class FuncionariaController {

    private final FuncionariaService service;

    public FuncionariaController(FuncionariaService service) {
        this.service = service;
    }
    @PostMapping("/criar")
    public ResponseEntity criarFuncionaria(@RequestBody Funcionaria funcionaria){
        service.criarFuncionaria(funcionaria);
        return ResponseEntity.status(200).body("Funcionaria Criada com Sucesso");
    }

    @GetMapping("/listar")
    public ResponseEntity listarFuncionarias(){
        return ResponseEntity.status(200).body(service.listarTodas());
    }
}