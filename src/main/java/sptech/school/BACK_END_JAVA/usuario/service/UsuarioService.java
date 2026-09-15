package sptech.school.BACK_END_JAVA.usuario.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;
import sptech.school.BACK_END_JAVA.usuario.entity.dto.request.UsuarioCreateRequestDto;
import sptech.school.BACK_END_JAVA.usuario.entity.dto.request.UsuarioUpdateRequestDto;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            ClienteRepository clienteRepository,
            ProfissionalRepository profissionalRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional
    public Usuario criar(UsuarioCreateRequestDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setTelefone(dto.getTelefone());
        usuario.setCpf(dto.getCpf());
        usuario.setSenha(dto.getSenha());
        usuario.setEmail(dto.getEmail());
        usuario.setTipo(dto.getTipo());
        usuario.setAtivo(dto.getAtivo() == null || dto.getAtivo());

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        usuario.setCriacao(LocalDateTime.now());

        Usuario usuarioCriado = usuarioRepository.save(usuario);

        if ("PROFISSIONAL".equalsIgnoreCase(usuarioCriado.getTipo())) {
            Profissional novoProfissional = new Profissional();
            novoProfissional.setUsuario(usuarioCriado);
            novoProfissional.setEspecialidade("Pendente");
            profissionalRepository.save(novoProfissional);
        } else if ("CLIENTE".equalsIgnoreCase(usuarioCriado.getTipo())) {
            Cliente novoCliente = new Cliente();
            novoCliente.setUsuario(usuarioCriado);
            clienteRepository.save(novoCliente);
        }

        return usuarioCriado;
    }

    public Usuario atualizar(UUID id, UsuarioUpdateRequestDto dto) {

        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        existente.setNome(dto.getNome());
        existente.setTelefone(dto.getTelefone());
        existente.setCpf(dto.getCpf());
        existente.setEmail(dto.getEmail());
        existente.setTipo(dto.getTipo());
        existente.setAtivo(dto.getAtivo());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            existente.setSenha(
                passwordEncoder.encode(dto.getSenha())
            );
        }

        return usuarioRepository.save(existente);
    }

    public void deletar(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        usuarioRepository.deleteById(id);
    }
}