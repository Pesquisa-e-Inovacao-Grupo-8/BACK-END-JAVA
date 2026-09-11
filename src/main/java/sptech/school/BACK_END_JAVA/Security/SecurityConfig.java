package sptech.school.BACK_END_JAVA.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sptech.school.BACK_END_JAVA.usuario.Impl.UserDetailsServiceImpl;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 👈 habilita @PreAuthorize nos controllers
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter,
                          UserDetailsServiceImpl userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> {})
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // preflight sempre liberado (senão CORS quebra)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // rotas públicas
                        .requestMatchers(
                                HttpMethod.POST,
                                "/auth/login",
                                "/auth/cadastrar"
                        ).permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/usuarios/**")
                        .hasAnyRole("CLIENTE", "PROFISSIONAL", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/servicos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/pacotes/**", "/pacoteServicos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profissionais/me/horarios")
                        .hasRole("PROFISSIONAL")
                        .requestMatchers(HttpMethod.PUT, "/profissionais/me/horarios")
                        .hasRole("PROFISSIONAL")
                        .requestMatchers(HttpMethod.GET, "/profissionais", "/profissionais/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profissionais/*/servicos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profissionais/*/horarios").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profissionais/{profissionalId}/horarios").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // rotas restritas por role
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/profissionais/meus-servicos/**", "/profissionais/vincular-servicos/**")
                        .hasAnyRole("PROFISSIONAL", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/agendamentos/disponibilidade")
                        .permitAll()
                        .requestMatchers(HttpMethod.POST, "/agendamentos")
                        .hasAnyRole("CLIENTE", "PROFISSIONAL", "ADMIN")
                        .requestMatchers("/agendamentos/**")
                        .hasAnyRole("CLIENTE", "PROFISSIONAL", "ADMIN")
                        .requestMatchers("/clientes/**", "/clientePacotes/**")
                        .hasAnyRole("CLIENTE", "ADMIN")
                        .requestMatchers("/pagamentos/**", "/comprovantes/**")
                        .hasAnyRole("CLIENTE", "ADMIN")
                        .requestMatchers("/servico-produtos/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/agendamentoServicos")
                        .hasAnyRole("CLIENTE", "PROFISSIONAL", "ADMIN")
                        .requestMatchers("/agendamentoServicos/**")
                        .hasAnyRole("CLIENTE", "PROFISSIONAL", "ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
                "https://renatahtokutomi.com",
                "https://www.renatahtokutomi.com",
                "https://qa.renatahtokutomi.com",
                "http://localhost:3000",
                "http://localhost:5173",
                "http://localhost:8000",
                "http://localhost:8001"
        ));

        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("Authorization"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}