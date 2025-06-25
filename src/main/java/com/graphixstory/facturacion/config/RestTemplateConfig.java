package com.graphixstory.facturacion.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Clase de configuración que define beans reutilizables para el proyecto.
 */
@Configuration
public class RestTemplateConfig {
    
/**
 * Define un bean de RestTemplate para realizar peticiones HTTP REST.
 *
 * @return instancia de RestTemplate
*/
 @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
