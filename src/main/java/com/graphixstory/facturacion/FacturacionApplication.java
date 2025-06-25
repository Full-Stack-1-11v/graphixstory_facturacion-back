package com.graphixstory.facturacion;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal que inicia la aplicación Spring Boot del sistema de facturación.
 */
@SpringBootApplication
public class FacturacionApplication {

	/**
     * Método principal que lanza la aplicación.
     *
     * @param args argumentos de línea de comandos
     */
	public static void main(String[] args) {
		SpringApplication.run(FacturacionApplication.class, args);
	}

}
