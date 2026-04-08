package sptech.school.BACK_END_JAVA.comprovante.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.comprovante.entity.Comprovante;
import sptech.school.BACK_END_JAVA.comprovante.repository.ComprovanteRepository;
import sptech.school.BACK_END_JAVA.pagamento.entity.Pagamento;
import sptech.school.BACK_END_JAVA.pagamento.repository.PagamentoRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ComprovanteService {
    private final ComprovanteRepository comprovanteRepository;
    private final PagamentoRepository pagamentoRepository;

    public ComprovanteService(ComprovanteRepository comprovanteRepository, PagamentoRepository pagamentoRepository) {
        this.comprovanteRepository = comprovanteRepository;
        this.pagamentoRepository = pagamentoRepository;
    }

    public List<Comprovante> listar() {return comprovanteRepository.findAll();}

    public Comprovante buscarPorId(UUID id) {
        return comprovanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comprovante não encontrado"));
    }

    public Comprovante criar(Comprovante comprovante, UUID pagamentoId) {

        Pagamento pagamento = pagamentoRepository.findById(pagamentoId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        comprovante.setPagamento(pagamento);

        return comprovanteRepository.save(comprovante);
    }

    public Comprovante atualizar(UUID id, Comprovante comprovante) {

        if (!comprovanteRepository.existsById(id)) {
            throw new RuntimeException("Comprovante não encontrado");
        }

        comprovante.setId(id);
        return comprovanteRepository.save(comprovante);
    }

    public void deletar(UUID id) {

        if (!comprovanteRepository.existsById(id)) {
            throw new RuntimeException("Comprovante não encontrado");
        }

        comprovanteRepository.deleteById(id);
    }

    //funções adicionais
}
