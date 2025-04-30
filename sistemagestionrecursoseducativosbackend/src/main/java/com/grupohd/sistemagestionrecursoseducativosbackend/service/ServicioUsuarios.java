package com.grupohd.sistemagestionrecursoseducativosbackend.service;

import com.grupohd.sistemagestionrecursoseducativosbackend.dto.ReqRes;
import com.grupohd.sistemagestionrecursoseducativosbackend.model.Usuario;
import com.grupohd. sistemagestionrecursoseducativosbackend.repository.UsuariosRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
/**
 * Servicio que gestiona las operaciones relacionadas con los usuarios del sistema,
 * también maneja la generación y validación de tokens JWT para autenticación
 */
@Service
public class ServicioUsuarios {

    @Autowired
    private UsuariosRepo usuariosRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ReqRes register(ReqRes registrationRequest) {
        ReqRes resp = new ReqRes();

        try {
            Usuario ourUser = new Usuario();
            ourUser.setCorreo(registrationRequest.getCorreo());
            ourUser.setDni(registrationRequest.getDni());
            ourUser.setNacimiento(registrationRequest.getNacimiento());
            ourUser.setDireccion(registrationRequest.getDireccion());
            ourUser.setNombre(registrationRequest.getNombre());
            ourUser.setApellidos(registrationRequest.getApellidos());
            ourUser.setCelular(registrationRequest.getCelular());

            ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            ourUser.setRol(registrationRequest.getRol());

            Usuario usuarioResult = usuariosRepo.save(ourUser);
            if (usuarioResult.getId() > 0) {
                resp.setUsuario(usuarioResult);
                resp.setMessage("Usuario registrado exitosamente");
                resp.setStatusCode(200);
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes login(ReqRes loginRequest) {
        ReqRes response = new ReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(),
                            loginRequest.getPassword()));
            var user = usuariosRepo.findByCorreo(loginRequest.getCorreo()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRol(user.getRol());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Inicio de sesión exitoso");

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(ReqRes refreshTokenReqiest) {
        ReqRes response = new ReqRes();
        try {
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            Usuario users = usuariosRepo.findByCorreo(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Token refrescado exitosamente");
            }
            response.setStatusCode(200);
            return response;

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<Usuario> result = usuariosRepo.findAll();
            if (!result.isEmpty()) {
                reqRes.setUsuarioList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Operacion exitosa");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No se encontraron usuarios");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Ocurrio un error: " + e.getMessage());
            return reqRes;
        }
    }

    public ReqRes getUsersById(Integer id) {
        ReqRes reqRes = new ReqRes();
        try {
            Usuario usersById = usuariosRepo.findById(id).orElseThrow(() -> new RuntimeException("usuario no encontrado"));
            reqRes.setUsuario(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Usuario con id '" + id + "' encontrado exitosamente");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Ocurrio un error: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes deleteUser(Integer userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Usuario> userOptional = usuariosRepo.findById(userId);
            if (userOptional.isPresent()) {
                usuariosRepo.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Usuario emliminado exitosamente");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Usuario no encontrado para eliminar");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Ocurrio un error al eliminar el usuario: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(Integer userId, Usuario updatedUser) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Usuario> userOptional = usuariosRepo.findById(userId);
            if (userOptional.isPresent()) {
                Usuario existingUser = userOptional.get();
                existingUser.setCorreo(updatedUser.getCorreo());
                existingUser.setCelular(updatedUser.getCelular());
                existingUser.setDni(updatedUser.getDni());
                existingUser.setNacimiento(updatedUser.getNacimiento());
                existingUser.setDireccion(updatedUser.getDireccion());
                existingUser.setNombre(updatedUser.getNombre());
                existingUser.setApellidos(updatedUser.getApellidos());
                existingUser.setRol(updatedUser.getRol());

                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                Usuario savedUser = usuariosRepo.save(existingUser);
                reqRes.setUsuario(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Usuario actualizado exitosamente");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Usuaro no encontrado para actualizar");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Ocurrió un error al actualizar el usuario: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes getMyInfo(String correo) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<Usuario> userOptional = usuariosRepo.findByCorreo(correo);
            if (userOptional.isPresent()) {
                reqRes.setUsuario(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("Información del usuario obtenida con éxito");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("Usuario no encontrado");
            }

        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Ocurrió un error al obtener la información del usuario: " + e.getMessage());
        }
        return reqRes;
    }
}
