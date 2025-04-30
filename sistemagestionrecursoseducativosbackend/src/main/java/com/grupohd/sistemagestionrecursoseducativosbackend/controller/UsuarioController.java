package com.grupohd.sistemagestionrecursoseducativosbackend.controller;

import com.grupohd.sistemagestionrecursoseducativosbackend.dto.ReqRes;
import com.grupohd.sistemagestionrecursoseducativosbackend.model.Usuario;
import com.grupohd.sistemagestionrecursoseducativosbackend.service.ServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para manejar las operaciones de usuario: registro, login, actualización y eliminación.
 */
@RestController
public class UsuarioController {

    @Autowired
    private ServicioUsuarios usersManagementService;
    /**
     * Este es un endpoint para registrar un nuevo usuario
     */
    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg) {
        return ResponseEntity.ok(usersManagementService.register(reg));
    }
    /**
     * Este es un endpoint para login de usuario
     */

    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    /**
     * Este es un endpoint para refrescar el token de autenticación
     */
    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req) {
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    /**
     * Este es un endpoint para obtener todos los usuarios
     */
    @GetMapping("/admin/get-all-users")
    public ResponseEntity<ReqRes> getAllUsers() {
        return ResponseEntity.ok(usersManagementService.getAllUsers());
    }
    /**
     * Este es un endpoint para obtener los detalles de un usuario por ID
     */
    @GetMapping("/admin/get-user/{userId}")
    public ResponseEntity<ReqRes> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));
    }
    /**
     * Este es un endpoint para actualizar los detalles de un usuario
     */
    @PutMapping("/admin/update-user/{userId}")
    public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId, @RequestBody Usuario user) {
        return ResponseEntity.ok(usersManagementService.updateUser(userId, user));
    }
    /**
     * Este es un endpoint para obtener el perfil del usuario autenticado
     */
    @GetMapping("/adminestudiantedocente/get-profile")
    public ResponseEntity<ReqRes> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String correo = authentication.getName();  // Aquí usamos "correo" como identificador
        ReqRes response = usersManagementService.getMyInfo(correo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    /**
     * Este es un endpoint para eliminar un usuario
     */
    @DeleteMapping("/admin/delete-user/{userId}")
    public ResponseEntity<ReqRes> deleteUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }
}
