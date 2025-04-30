package com.grupohd.sistemagestionrecursoseducativosbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.grupohd.sistemagestionrecursoseducativosbackend.model.Usuario;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String rol;
    private String password;

    private String correo;
    private String dni;
    private LocalDate nacimiento;
    private String direccion;
    private String nombre;
    private String celular;
    private String apellidos;

    private Usuario usuario;
    private List<Usuario> usuarioList;
}
