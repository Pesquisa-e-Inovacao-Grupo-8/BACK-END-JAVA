package sptech.school.BACK_END_JAVA.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.entity.Funcionaria;
import sptech.school.BACK_END_JAVA.repository.FuncionariaRepository;


import java.util.List;

@Service
public class FuncionariaService {
    private final FuncionariaRepository repository;

    public FuncionariaService(FuncionariaRepository repository) {
        this.repository = repository;
    }

    public Boolean criarFuncionaria(Funcionaria funcionaria){
        repository.save(funcionaria);
        return true;
    }

    public List<Funcionaria> listarTodas(){
        return repository.findAll();
    }
}
