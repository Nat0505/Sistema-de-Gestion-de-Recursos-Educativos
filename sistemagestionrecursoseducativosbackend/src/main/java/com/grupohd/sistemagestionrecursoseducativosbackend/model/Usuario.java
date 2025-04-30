package com.grupohd.sistemagestionrecursoseducativosbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


/**
 * Clase que representa a un usuario del sistema.
 */
@Entity
@Table(name = "usuarios")
@Data // Lombok genera automáticamente los getters, setters, toString, equals, etc.
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String celular;
    private String correo;
    private String dni;
    private String nombre;
    private String apellidos;
    private String password;
    private String rol;
    private String direccion;
    private LocalDate nacimiento;


    /**
     * Devuelve el rol del usuario en forma que Spring Security pueda entender
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol));
    }

    /**
     * Este método devuelve el identificador del usuario en el sistema de seguridad.
     * En este caso, usamos el correo institucional
     */
    @Override
    public String getUsername() {
        return correo;
    }

    // Los siguientes métodos indican si la cuenta está activa o no
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
