package sptech.school.BACK_END_JAVA.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // mantém o método antigo (caso use em algum lugar sem claims extras)
    public String generateToken(String username) {
        return generateToken(username, new HashMap<>());
    }

    // novo: gera token com claims extras (id, nome, tipo, etc)
    public String generateToken(String username, Map<String, Object> extraClaims) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // método de conveniência já passando os dados do usuário
    public String generateToken(String username, String idUsuario, String nome, String tipo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", idUsuario);
        claims.put("nome", nome);
        claims.put("tipo", tipo); // ex: "CLIENTE" ou "PROFISSIONAL"
        return generateToken(username, claims);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractId(String token) {
        return extractAllClaims(token).get("id", String.class);
    }

    public String extractNome(String token) {
        return extractAllClaims(token).get("nome", String.class);
    }

    public String extractTipo(String token) {
        return extractAllClaims(token).get("tipo", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        String username = extractUsername(token);
        Date expirationDate = extractAllClaims(token).getExpiration();

        return username.equals(user.getUsername())
                && expirationDate.after(new Date());
    }
}