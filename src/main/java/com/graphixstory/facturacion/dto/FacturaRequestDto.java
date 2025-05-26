package com.graphixstory.facturacion.dto;

import lombok.Data; // Si usas Lombok, para getters, setters, etc.
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 

public class FacturaRequestDto {

    private Double montoTotal;
    private String estado;
    private String fechaEmision; 
    private Integer usuarioId; 
}

