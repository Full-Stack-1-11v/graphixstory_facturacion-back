package com.graphixstory.facturacion.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.graphixstory.facturacion.dto.UsuarioDTO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;


@Component
public class UsuarioClient {
 private final RestTemplate restTemplate;

    @Value("http://localhost:8081/api") 
    private String baseUrl;

    public UsuarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
