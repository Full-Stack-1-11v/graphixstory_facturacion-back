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


/**
 * Controlador REST para gestionar las operaciones relacionadas con la
 * facturacion.
 * Proporciona endpoints para listar, crear y obtener facturas.
 */

@RestController
@RequestMapping("/api/facturas")
@Tag (name="Facturas", description = "Operaciones relacionadas con la facturacion Edutech")
public class FacturaController {

private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

     /**
     * Obtiene una lista de todas las facturas.
     * 
     * @return Lista de objetos {@link Factura}.
     */
    @GetMapping
    @Operation(summary = "Obtener todas las facturas", description ="Devuelve una lista de todas las facturas registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de facturas obtenida correctamente")
    })
    public List<Factura> getAllFacturas() {
        return facturaService.getAllFacturas();
    }
    
     /**
     * Obtiene una factura junto con datos del usuario asociado.
     * 
     * @param id ID de la factura que se desea obtener.
     * @return Lista de objetos {@link Factura} con los detalles del usuario asociado.
     */
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


     /**
     * Obtiene una factura por su ID.
     * 
     * @param id ID de la factura que se desea obtener.
     * @return Lista de objetos {@link Factura}.
     */
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


    /**
     * Crea una nueva factura en el sistema.
     * 
     * @param factura Objeto {@link Factura} con los datos de la factura.
     * @return Objeto {@link Factura} creado.
     */
    @PostMapping
    @Operation(summary = "Crear factura" , description = "Crea una nueva factura con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario no existente")
    })
    public Factura createFactura(@RequestBody FacturaRequestDto facturaRequestDto) {
        return facturaService.saveFactura(facturaRequestDto);
    }


      /**
     * Elimina una factura del sistema por su ID.
     * 
     * @param id ID de la factura a eliminar.
     * @return Respuesta con código de estado HTTP.
     */
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




    

