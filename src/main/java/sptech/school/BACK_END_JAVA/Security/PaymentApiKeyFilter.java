package sptech.school.BACK_END_JAVA.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class PaymentApiKeyFilter extends OncePerRequestFilter {

    private final String configuredApiKey;

    public PaymentApiKeyFilter(@Value("${payment.api.key:}") String configuredApiKey) {
        this.configuredApiKey = configuredApiKey;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String requestApiKey = request.getHeader("API-KEY");

        if (!configuredApiKey.isBlank() && configuredApiKey.equals(requestApiKey)) {
            var authentication = new UsernamePasswordAuthenticationToken(
                    "payment-service",
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_PAYMENT"))
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}