package com.graphixstory.facturacion.dto;

import lombok.Data; 
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

/**
 * DTO utilizado para recibir los datos necesarios para crear una nueva factura.
 * Incluye validaciones y anotaciones para la documentación Swagger.
 */
@Data 
@NoArgsConstructor 
@AllArgsConstructor 

@Schema(description = "DTO que contiene los datos necesarios para crear una factura")
public class FacturaRequestDto {

    @Schema(description = "Monto total de la factura", example = "19990.0")
    @NotNull(message = "El monto total es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private Double montoTotal;

    @Schema(description = "Estado de la factura", example = "PAGADA")
    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @Schema(description = "Fecha de emisión (formato ISO)", example = "2025-06-01T10:15:30")
    private String fechaEmision; 

    @Schema(description = "ID del usuario", example = "1", required = true)
    @NotNull(message = "El ID del usuario es obligatorio")
    private Integer usuarioId; 
}

