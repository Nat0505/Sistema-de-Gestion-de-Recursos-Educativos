package com.grupohd.sistemagestionrecursoseducativosbackend.service;

import com.grupohd.sistemagestionrecursoseducativosbackend.model.Usuario;
import com.grupohd.sistemagestionrecursoseducativosbackend.repository.UsuariosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



/**
 * Este servicio busca un usuario en la base de datos por su correo electrónico y devuelve sus detalles para la autenticación.
 */

@Service
public class DetalleUsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuariosRepo usuariosRepo;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario user = usuariosRepo.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo));
        return user;
    }
}
