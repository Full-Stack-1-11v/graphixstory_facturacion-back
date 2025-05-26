package com.graphixstory.facturacion.controller;

import com.graphixstory.facturacion.dto.FacturaRequestDto;
import com.graphixstory.facturacion.dto.FacturaUsuarioDto;
import com.graphixstory.facturacion.model.Factura;
import com.graphixstory.facturacion.service.FacturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public List<Factura> getAllFacturas() {
        return facturaService.getAllFacturas();
    }
    
    @GetMapping("/{id}/detalle")
    public ResponseEntity<FacturaUsuarioDto> getFacturaConUsuario(@PathVariable Long id) {
        return facturaService.getFacturaConUsuarioById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> getFacturaById(@PathVariable Long id) {
        return facturaService.getFacturaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Factura createFactura(@RequestBody FacturaRequestDto facturaRequestDto) {
        return facturaService.saveFactura(facturaRequestDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {
        facturaService.deleteFactura(id);
        return ResponseEntity.noContent().build();
    }
}




    

