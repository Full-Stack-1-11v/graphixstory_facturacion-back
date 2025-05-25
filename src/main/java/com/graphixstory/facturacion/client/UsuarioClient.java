package com.graphixstory.facturacion.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.graphixstory.facturacion.model.UsuarioDTO;

import org.springframework.beans.factory.annotation.Value;


@Component
public class UsuarioClient {
 private final RestTemplate restTemplate;

    @Value("${usuarios.api.url}")
    private String baseUrl;

    public UsuarioClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UsuarioDTO getUsuarioById(Long id) {
        return restTemplate.getForObject(baseUrl + "/usuarios/" + id, UsuarioDTO.class);
    }
}
