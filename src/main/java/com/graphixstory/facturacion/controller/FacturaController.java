package com.graphixstory.facturacion.controller;

import com.graphixstory.facturacion.dto.FacturaRequestDto;
import com.graphixstory.facturacion.dto.FacturaUsuarioDto;
import com.graphixstory.facturacion.model.Factura;
import com.graphixstory.facturacion.service.FacturaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@Tag (name="Facturas", description = "Operaciones relacionadas con la facturacion Edutech")
public class FacturaController {

private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las facturas", description ="Devuelve una lista de todas las facturas registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida correctamente")
    })
    public List<Factura> getAllFacturas() {
        return facturaService.getAllFacturas();
    }
    
    @GetMapping("/{id}/detalle")
    @Operation(summary = "Obtener las facturas con detalles de usuario" , description = "Devuelve los datos de una factura junto con los detalles del usuario asociado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura con usuario encontrada"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada o sin usuario asociado")
    })
    public ResponseEntity<FacturaUsuarioDto> getFacturaConUsuario( @Parameter(description = "ID de la factura a consultar", example = "1")@PathVariable Long id) {
        return facturaService.getFacturaConUsuarioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener la factura por su id", description = "Devuelve una factura según su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura encontrada"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<Factura> getFacturaById(@PathVariable Long id) {
        return facturaService.getFacturaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear factura" , description = "Crea una nueva factura con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario no existente")
    })
    public Factura createFactura(@RequestBody FacturaRequestDto facturaRequestDto) {
        return facturaService.saveFactura(facturaRequestDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una factura", description = "Elimina una factura por su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {
        facturaService.deleteFactura(id);
        return ResponseEntity.noContent().build();
    }
}




    

