package sptech.school.BACK_END_JAVA.usuario.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

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

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuÃ¡rio nÃ£o encontrado"));
    }

    @Transactional
    public Usuario criar(Usuario usuario) {

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

    public Usuario atualizar(Integer id, Usuario usuario) {

        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuÃ¡rio nÃ£o encontrado"));

        existente.setNome(usuario.getNome());
        existente.setTelefone(usuario.getTelefone());
        existente.setCpf(usuario.getCpf());
        existente.setEmail(usuario.getEmail());
        existente.setTipo(usuario.getTipo());
        existente.setAtivo(usuario.getAtivo());

        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            existente.setSenha(
                    passwordEncoder.encode(usuario.getSenha())
            );
        }

        return usuarioRepository.save(existente);
    }

    public void deletar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("UsuÃ¡rio nÃ£o encontrado");
        }

        usuarioRepository.deleteById(id);
    }
}

