package sptech.school.BACK_END_JAVA.agendamento.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;
import sptech.school.BACK_END_JAVA.agendamento.repository.AgendamentoRepository;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;

import java.util.List;
import java.util.UUID;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, ClienteRepository clienteRepository, ProfissionalRepository profissionalRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
    }

    public List<Agendamento> listar() {return agendamentoRepository.findAll();}

    public Agendamento buscarPorId(UUID id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }

    public Agendamento criar(Agendamento agendamento, UUID clienteId, UUID profissionalId) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        agendamento.setCliente(cliente);
        agendamento.setProfissional(profissional);

        return agendamentoRepository.save(agendamento);
    }

    public Agendamento atualizar(UUID id, Agendamento agendamento) {

        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado");
        }

        agendamento.setId(id);
        return agendamentoRepository.save(agendamento);
    }

    public void deletar(UUID id) {

        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado");
        }

        agendamentoRepository.deleteById(id);
    }


    //restante das funções
}
