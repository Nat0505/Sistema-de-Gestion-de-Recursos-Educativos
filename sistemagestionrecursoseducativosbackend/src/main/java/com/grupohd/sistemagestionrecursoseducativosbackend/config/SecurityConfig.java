package com.grupohd.sistemagestionrecursoseducativosbackend.config;

import com.grupohd.sistemagestionrecursoseducativosbackend.service.DetalleUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Esta clase configura toda la seguridad de la aplicación.
 * Define qué rutas necesitan autenticación, qué roles acceden a qué, y cómo se maneja el JWT.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private DetalleUsuarioServicio detalleUsuarioServicio;

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**", "/public/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") // Solo ADMIN puede entrar aquí
                        .requestMatchers("/docente/**").hasAuthority("DOCENTE") // Solo DOCENTE puede entrar aqui
                        .requestMatchers("/estudiante/**").hasAuthority("ESTUDIANTE") //  Solo ESTUDIANTE puede entrar aquí
                        .requestMatchers("/adminestudiantedocente/**").hasAnyAuthority("ADMIN", "ESTUDIANTE", "DOCENTE")
                        .anyRequest().authenticated()) // Otras rutas requieren autenticación
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Filtro JWT antes de la autenticación por usuario y contraseña
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(detalleUsuarioServicio);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Este es el codificador de contraseñas
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
