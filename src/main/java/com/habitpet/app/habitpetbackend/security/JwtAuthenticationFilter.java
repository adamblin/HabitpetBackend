package com.habitpet.app.habitpetbackend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    // Rutas excluidas del filtro
    private static final List<String> EXCLUDED_PATHS = List.of(
            "/auth/", "/h2-console/", "/error"
    );

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            Claims claims = jwtTokenProvider.getClaims(token);
            System.out.println("✅ Claims extraídos: " + claims);

            String email = claims.getSubject();
            System.out.println("✅ Email extraído del token: " + email);

            if (email != null) {
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    if (userDetails != null) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("✅ Usuario autenticado: " + email);
                    } else {
                        System.out.println("❌ No se encontró usuario con el email: " + email);
                    }
                } catch (Exception e) {
                    System.out.println("❌ Error al cargar el usuario: " + e.getMessage());
                }
            }
        } else {
            SecurityContextHolder.clearContext();
            System.out.println("❌ Token inválido o no presente.");
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null) {
            if (!request.getRequestURI().startsWith("/h2-console")) {
                System.out.println("❌ No se encontró la cabecera Authorization.");
            }
            return null;
        }

        System.out.println("🔎 Header Authorization recibido: " + bearerToken);

        if (bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            System.out.println("✅ Token extraído correctamente.");
            return token;
        }

        System.out.println("❌ La cabecera Authorization no tiene formato Bearer.");
        return null;
    }
}
