package sptech.school.BACK_END_JAVA.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.entity.Agendamento;
import sptech.school.BACK_END_JAVA.exception.AgendamentoOrdemJaExisteException;
import sptech.school.BACK_END_JAVA.exception.AgendamentoNotFoundException;
import sptech.school.BACK_END_JAVA.repository.AgendamentoRepository;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repository;

    public AgendamentoService(AgendamentoRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity criarAgendamento(Agendamento agendamento){
        if(repository.existsByOrdemAgendamento(agendamento.getOrdemAgendamento())){
            throw new AgendamentoOrdemJaExisteException("Agendamento já existe");
        }
        repository.save(agendamento);
        return ResponseEntity.status(200).body("Agendamento Criado com Sucesso");
    }

    public Agendamento buscarPorOrdemAgendamento(String ordemAgendamento) {
        Agendamento agendamento = repository.findByOrdemAgendamento(ordemAgendamento);
        if (agendamento == null) {
            throw new AgendamentoNotFoundException("Agendamento não encontrado");
        }
        return agendamento;
    }

    public Agendamento atualizarAgendamento(Agendamento agendamento){
        if(!repository.existsByOrdemAgendamento(agendamento.getOrdemAgendamento())){
            throw new AgendamentoNotFoundException("Agendamento não existe");
        }
        repository.save(agendamento);
        return agendamento;
    }

    public Agendamento atualizarAgendamento(String ordemAgendamento, Agendamento dados) {
        Agendamento agendamento = repository.findByOrdemAgendamento(ordemAgendamento);
        if (agendamento.getOrdemAgendamento() == null) {
            throw new AgendamentoNotFoundException("Agendamento não encontrado");
        }
//        agendamento.setNome(dados.getNome());
//        agendamento.setData(dados.getData());
//        outros campos...

        return repository.save(agendamento);
        }


}