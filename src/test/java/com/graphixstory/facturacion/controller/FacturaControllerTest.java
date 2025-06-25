package com.graphixstory.facturacion.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphixstory.facturacion.dto.FacturaRequestDto;
import com.graphixstory.facturacion.dto.FacturaUsuarioDto;
import com.graphixstory.facturacion.model.Factura;
import com.graphixstory.facturacion.service.FacturaService;


import org.springframework.http.MediaType;


@WebMvcTest(FacturaController.class)
public class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService facturaService;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    @Test
    public void testGetAllFacturas() throws Exception {

        Factura factura = new Factura();
        factura.setId(1L);
        factura.setUsuarioId(1);
        factura.setMontoTotal(1000.0);
        factura.setEstado("PAGADA");
        factura.setFechaEmision(LocalDateTime.of(2024, 10, 25, 0, 0));

        when(facturaService.getAllFacturas()).thenReturn(List.of(factura));

        mockMvc.perform(get("/api/facturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    public void testGetFacturaByIdExistente() throws Exception {
        
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setUsuarioId(1);
        factura.setEstado("PAGADA");
        factura.setMontoTotal(1500.0);

        when(facturaService.getFacturaById(1L)).thenReturn(Optional.of(factura));

        mockMvc.perform(get("/api/facturas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("PAGADA"));
    }

    @Test
    public void testGetFacturaByIdNoExistente() throws Exception {

        when(facturaService.getFacturaById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/facturas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetFacturaConUsuarioExistente() throws Exception {

        FacturaUsuarioDto dto = new FacturaUsuarioDto();
        dto.setId(1L);
        dto.setUsuarioId(1);
        dto.setNombreUsuario("Juan Pérez");
        dto.setEstado("PAGADA");

        when(facturaService.getFacturaConUsuarioById(1L)).thenReturn(Optional.of(dto));

    mockMvc.perform(get("/api/facturas/1/detalle"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombreUsuario").value("Juan Pérez"))
            .andExpect(jsonPath("$.estado").value("PAGADA"));
    }

    @Test
    public void testGetFacturaConUsuarioNoExistente() throws Exception {

        when(facturaService.getFacturaConUsuarioById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/facturas/99/detalle"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFactura() throws Exception {

        FacturaRequestDto requestDto = new FacturaRequestDto();
        requestDto.setUsuarioId(1);
        requestDto.setEstado("PAGADA");
        requestDto.setMontoTotal(1200.0);
        requestDto.setFechaEmision("2025-06-01T10:15:30");

        Factura factura = new Factura();
        factura.setId(1L);
        factura.setUsuarioId(1);
        factura.setEstado("PAGADA");
        factura.setMontoTotal(1200.0);

        when(facturaService.saveFactura(any(FacturaRequestDto.class))).thenReturn(factura);

        mockMvc.perform(post("/api/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "usuarioId": 1,
                            "estado": "PAGADA",
                            "montoTotal": 1200.0,
                            "fechaEmision": "2025-06-01T10:15:30"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("PAGADA"));
    }

    @Test
    public void testDeleteFactura() throws Exception {

        mockMvc.perform(delete("/api/facturas/1"))
                .andExpect(status().isNoContent());

        verify(facturaService).deleteFactura(1L);
    }

}
