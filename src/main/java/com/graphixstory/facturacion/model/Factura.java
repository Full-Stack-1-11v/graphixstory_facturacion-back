package com.graphixstory.facturacion.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una factura en la base de datos.
 * Contiene información del usuario, monto total, estado y fecha de emisión.
 */
@Entity
@Table(name = "facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {

    /**
     * Identificador único de la factura.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID del usuario asociado a la factura.
     * Campo obligatorio.
     */
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    /**
     * Monto total de la factura.
     * Campo obligatorio.
     */
    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    /**
     * Fecha de emisión de la factura.
     * Campo obligatorio.
     */
    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;

    /**
     * Estado de la factura (ej. PENDIENTE, PAGADA).
     * Campo obligatorio.
     */
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;
}
