package com.graphixstory.facturacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

/**
 * Clase de configuración para la documentación Swagger (OpenAPI).
 * Define los metadatos principales del sistema de facturación de Edutech.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configura la instancia de OpenAPI con título, versión y descripción de la API.
     *
     * @return objeto OpenAPI con información personalizada del proyecto
     */
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API 2025 Facturacion Edutech")
                        .version("1.0")
                        .description("Documentacion de la API  para el sistema de Facturacion de la plataforma de cursos Edutech"));

    }
}
