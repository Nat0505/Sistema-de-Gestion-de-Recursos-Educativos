package com.grupohd.sistemagestionrecursoseducativosbackend.config;

import com.grupohd.sistemagestionrecursoseducativosbackend.service.JWTUtils;
import com.grupohd.sistemagestionrecursoseducativosbackend.service.DetalleUsuarioServicio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Filtro que se ejecuta una vez por petición
 */
@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private DetalleUsuarioServicio detalleUsuarioServicio;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtenemos el header "Authorization" donde debería venir el token
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userCorreo;

        // Si no hay token, se sigue con la cadena de filtros sin hacer nada
        if (authHeader == null || authHeader.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken = authHeader.substring(7);
        // Extraemos el correo del token
        userCorreo = jwtUtils.extractUsername(jwtToken);
        // Si el usuario no está autenticado aún y el correo es válido
        if (userCorreo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = detalleUsuarioServicio.loadUserByUsername(userCorreo);
            // Verificamos que el token realmente sea válido
            if (jwtUtils.isTokenValid(jwtToken, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        // Continuamos con la petición normalmente
        filterChain.doFilter(request, response);
    }
}
