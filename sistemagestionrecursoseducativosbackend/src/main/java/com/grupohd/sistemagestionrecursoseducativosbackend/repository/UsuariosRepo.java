package com.grupohd.sistemagestionrecursoseducativosbackend.repository;

import com.grupohd.sistemagestionrecursoseducativosbackend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repositorio para interactuar con la tabla de usuarios en la base de datos
 */
public interface UsuariosRepo extends JpaRepository<Usuario, Integer> {

    // Buscar un usuario por su correo electrónico
    Optional<Usuario> findByCorreo(String correo);
}
