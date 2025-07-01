package com.graphixstory.facturacion.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphixstory.facturacion.assemblers.FacturaModelAssembler;
import com.graphixstory.facturacion.dto.FacturaRequestDto;
import com.graphixstory.facturacion.dto.FacturaUsuarioDto;
import com.graphixstory.facturacion.model.Factura;
import com.graphixstory.facturacion.service.FacturaService;


@WebMvcTest(FacturaController.class)
public class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService facturaService;

    @MockBean
    private FacturaModelAssembler facturaModelAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllFacturas() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setUsuarioId(1);
        factura.setMontoTotal(1000.0);
        factura.setEstado("PAGADA");
        factura.setFechaEmision(LocalDateTime.of(2024, 10, 25, 0, 0));

        when(facturaService.getAllFacturas()).thenReturn(List.of(factura));
        when(facturaModelAssembler.toModel(factura)).thenReturn(EntityModel.of(factura));

        mockMvc.perform(get("/api/facturas")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.facturaList[0].id").value(1L))
                .andExpect(jsonPath("$._embedded.facturaList[0].usuarioId").value(1L))
                .andExpect(jsonPath("$._embedded.facturaList[0].montoTotal").value(1000.0))
                .andExpect(jsonPath("$._embedded.facturaList[0].estado").value("PAGADA"));
    }

    @Test
    public void testGetFacturaByIdExistente() throws Exception {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setUsuarioId(1);
        factura.setEstado("PAGADA");
        factura.setMontoTotal(1500.0);
        factura.setFechaEmision(LocalDateTime.now());

        when(facturaService.getFacturaById(1L)).thenReturn(Optional.of(factura));
        when(facturaModelAssembler.toModel(factura)).thenReturn(EntityModel.of(factura));

        mockMvc.perform(get("/api/facturas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.estado").value("PAGADA"))
                .andExpect(jsonPath("$.montoTotal").value(1500.0));
    }

    @Test
    public void testGetFacturaByIdNoExistente() throws Exception {
        when(facturaService.getFacturaById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/facturas/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetFacturaConUsuarioExistente() throws Exception {
        FacturaUsuarioDto dto = new FacturaUsuarioDto();
        dto.setId(1L);
        dto.setUsuarioId(1);
        dto.setNombreUsuario("Juan Pérez");
        dto.setEstado("PAGADA");
        dto.setMontoTotal(2500.0);

        when(facturaService.getFacturaConUsuarioById(1L)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/api/facturas/1/detalle")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.nombreUsuario").value("Juan Pérez"))
                .andExpect(jsonPath("$.estado").value("PAGADA"))
                .andExpect(jsonPath("$.montoTotal").value(2500.0));
    }

    @Test
    public void testGetFacturaConUsuarioNoExistente() throws Exception {
        when(facturaService.getFacturaConUsuarioById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/facturas/99/detalle")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateFactura() throws Exception {
        FacturaRequestDto requestDto = new FacturaRequestDto();
        requestDto.setUsuarioId(1);
        requestDto.setEstado("PENDIENTE");
        requestDto.setMontoTotal(1200.0);
        requestDto.setFechaEmision("2025-06-01T10:15:30");

        Factura facturaCreada = new Factura();
        facturaCreada.setId(1L);
        facturaCreada.setUsuarioId(requestDto.getUsuarioId());
        facturaCreada.setEstado(requestDto.getEstado());
        facturaCreada.setMontoTotal(requestDto.getMontoTotal());
        facturaCreada.setFechaEmision(LocalDateTime.parse(requestDto.getFechaEmision()));

        when(facturaService.saveFactura(any(FacturaRequestDto.class))).thenReturn(facturaCreada);

        mockMvc.perform(post("/api/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.estado").value("PENDIENTE"))
                .andExpect(jsonPath("$.montoTotal").value(1200.0));
    }

    @Test
    public void testDeleteFactura() throws Exception {
        mockMvc.perform(delete("/api/facturas/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(facturaService).deleteFactura(1L);
    }
}
