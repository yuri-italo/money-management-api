package br.com.alura.moneymanagementapi.config.security;

import br.com.alura.moneymanagementapi.model.User;
import br.com.alura.moneymanagementapi.repository.UserRepository;
import br.com.alura.moneymanagementapi.config.security.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenFilterAuthentication extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public TokenFilterAuthentication(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = tokenRecovery(request);
        boolean isValid = tokenService.isAValidToken(token);

        if (isValid)
            authenticateUser(token);

        filterChain.doFilter(request,response);
    }

    private void authenticateUser(String token) {
        Long userId = tokenService.getUserId(token);
        User user = userRepository.findById(userId).get();

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private String tokenRecovery(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer "))
            return null;

        return token.substring(7,token.length());
    }
}
