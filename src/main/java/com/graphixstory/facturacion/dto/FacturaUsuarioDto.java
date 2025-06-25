package com.graphixstory.facturacion.dto;

import com.graphixstory.facturacion.model.Factura;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO que combina los datos de una factura con la información del usuario asociado.
 * Se utiliza para enviar datos compuestos al cliente.
 */
@Schema(description = "DTO que combina los datos de una factura con la información del usuario asociado")
public class FacturaUsuarioDto {

    @Schema(description = "ID de la factura", example = "1")
    private Long id;

    @Schema(description = "Monto total de la factura", example = "24990.0")
    private Double montoTotal;

    @Schema(description = "Estado de la factura", example = "PAGADA")
    private String estado;

    @Schema(description = "Fecha de emisión en formato ISO", example = "2025-06-01T10:15:30")
    private String fechaEmision;

    @Schema(description = "ID del usuario asociado a la factura", example = "3")
    private Integer usuarioId;

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombreUsuario;

    @Schema(description = "RUN del usuario", example = "12.345.678-9")
    private String runUsuario;

    /**
     * Constructor vacío necesario para frameworks y serialización.
     */
    public FacturaUsuarioDto() {
    }

    /**
     * Constructor que crea un DTO combinando datos de factura y usuario.
     *
     * @param factura Objeto factura del sistema
     * @param usuario Objeto UsuarioDTO con los datos del usuario asociado
     */
    public FacturaUsuarioDto(Factura factura, UsuarioDTO usuario) {
        this.id = factura.getId();
        this.montoTotal = factura.getMontoTotal();
        this.estado = factura.getEstado();

        if (factura.getFechaEmision() != null) {
            this.fechaEmision = factura.getFechaEmision().toString();
        }

        if (usuario != null) {
            this.usuarioId = usuario.getId();
            this.nombreUsuario = usuario.getNombre() + " " + usuario.getApellido(); 
            this.runUsuario = usuario.getRun();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getRunUsuario() {
        return runUsuario;
    }

    public void setRunUsuario(String runUsuario) {
        this.runUsuario = runUsuario;
    }
}
