package sptech.school.BACK_END_JAVA.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.Security.DTO.*;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario; // ajuste o pacote real
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository; // ajuste o pacote real
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.repository.ClienteRepository;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/validate-access")
    public ResponseEntity<ValidarAcessoResponse> validarAcesso(
            @RequestHeader("Authorization") String authHeader) {

        System.out.println("TOKEN :" + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                    .body(new ValidarAcessoResponse(false, null));
        }

        String token = authHeader.substring(7);

        try {
            String tipo = jwtUtil.extractTipo(token);

            // extractUsername força a validação de assinatura + expiração
            jwtUtil.extractUsername(token);

            return ResponseEntity.ok(new ValidarAcessoResponse(true, tipo));

        } catch (Exception ex) {
            // token expirado, adulterado ou malformado
            return ResponseEntity.status(401)
                    .body(new ValidarAcessoResponse(false, null));
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<CadastrarResponse> cadastrar(@RequestBody CadastrarRequest request) {

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(409)
                    .body(new CadastrarResponse("Email já cadastrado", false));
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setCpf(request.getCpf());
        usuario.setTelefone(request.getTelefone());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setTipo("CLIENTE"); // fixo, nunca vem do request
        usuario.setAtivo(true);
        usuario.setCriacao(java.time.LocalDateTime.now());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuarioSalvo);
        clienteRepository.save(cliente);

        return ResponseEntity.ok(new CadastrarResponse("Usuário cadastrado com sucesso", true));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication =
                    authManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(),
                                    request.getSenha()
                            )
                    );

            Usuario usuario = usuarioRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            String token = jwtUtil.generateToken(
                    authentication.getName(),
                    usuario.getId().toString(),
                    usuario.getNome(),
                    usuario.getTipo()
            );

            return ResponseEntity.ok(new LoginResponse(token));

        } catch (Exception ex) {
            return ResponseEntity.status(401).body(new LoginResponse(null));
        }
    }
}