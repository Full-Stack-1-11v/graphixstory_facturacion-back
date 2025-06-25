package com.graphixstory.facturacion.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.graphixstory.facturacion.dto.UsuarioDTO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;

/**
 * Cliente REST que se comunica con el microservicio de usuarios.
 * Permite obtener información de un usuario a través de su ID.
 */
@Component
public class UsuarioClient {
 private final RestTemplate restTemplate;

  /**
     * URL base del microservicio de usuarios.
     * Se configura desde las propiedades de la aplicación.
     */
    @Value("http://localhost:8081/api") 
    private String baseUrl;

    /**
     * Constructor del cliente que inyecta un RestTemplate.
     *
     * @param restTemplate cliente HTTP usado para realizar solicitudes REST
     */
    public UsuarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * Obtiene los datos de un usuario a partir de su ID.
     *
     * @param id ID del usuario a consultar
     * @return Optional con UsuarioDTO si se encuentra, o vacío si ocurre un error o no existe
     */
   public Optional<UsuarioDTO> getUsuarioById(Integer id) {
    try {
        UsuarioDTO usuario = restTemplate.getForObject(baseUrl + "/usuarios/" + id, UsuarioDTO.class);
        return Optional.ofNullable(usuario);
    } catch (Exception e) {
        e.printStackTrace();
        return Optional.empty();
        }
    }
}
