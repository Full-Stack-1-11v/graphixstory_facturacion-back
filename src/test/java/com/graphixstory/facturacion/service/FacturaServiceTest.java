package com.graphixstory.facturacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.graphixstory.facturacion.client.UsuarioClient;
import com.graphixstory.facturacion.dto.FacturaRequestDto;
import com.graphixstory.facturacion.dto.UsuarioDTO;
import com.graphixstory.facturacion.model.Factura;
import com.graphixstory.facturacion.repository.FacturaRepository;

@SpringBootTest
public class FacturaServiceTest {

    @Autowired
    private FacturaService facturaService;

    @MockBean
    private FacturaRepository facturaRepository;

    @MockBean
    private UsuarioClient usuarioClient;

    @Test
    public void testGetAllFacturas(){

          // Crear una factura de ejemplo
          Factura factura = new Factura();
            factura.setUsuarioId(1);
            factura.setMontoTotal(250000.0);
            factura.setEstado("PAGADA");
            factura.setFechaEmision(LocalDateTime.of(2024, 10, 25, 0, 0));

          // Configurar el mock para devolver una lista con esa factura
          when(facturaRepository.findAll()).thenReturn(List.of(factura));
  
          // Llamar al método de servicio
          List<Factura> facturas = facturaService.getAllFacturas();
  
          // Verificar que la lista no esté vacía y que contiene la factura correcta
          assertNotNull(facturas);
          assertEquals(1, facturas.size());
          assertEquals(1, facturas.get(0).getUsuarioId());
          assertEquals(250000.0, facturas.get(0).getMontoTotal());
          assertEquals(LocalDateTime.of(2024,10,25,0,0), facturas.get(0).getFechaEmision());
          assertEquals("PAGADA", facturas.get(0).getEstado());
  
          // Verificar que findAll() fue llamado en el repositorio
          verify(facturaRepository).findAll();

    }

    @Test
    public void testGetFacturaById() {
       
        Long facturaId = 1L;
        Factura factura = new Factura();
        factura.setId(facturaId);
        factura.setMontoTotal(150000.0);
        factura.setEstado("PAGADA");
        factura.setFechaEmision(LocalDateTime.of(2024, 10, 25, 15, 30));
        factura.setUsuarioId(1);

        when(facturaRepository.findById(facturaId)).thenReturn(Optional.of(factura));

        Optional<Factura> resultado = facturaService.getFacturaById(facturaId);

      
        assertTrue(resultado.isPresent());
        assertEquals(facturaId, resultado.get().getId());
        assertEquals(150000.0, resultado.get().getMontoTotal());
        assertEquals("PAGADA", resultado.get().getEstado());
        assertEquals(LocalDateTime.of(2024, 10, 25, 15, 30), resultado.get().getFechaEmision());
        assertEquals(1, resultado.get().getUsuarioId());
    }

    @Test
    public void testGetFacturaByIdNoExistente() {
   
        Long idFactura = 99L;
        when(facturaRepository.findById(idFactura)).thenReturn(Optional.empty());

        Optional<Factura> resultado = facturaService.getFacturaById(idFactura);

        assertFalse(resultado.isPresent());
        verify(facturaRepository).findById(idFactura);
    }

    @Test
    public void testSaveFacturaValida() {
        
        FacturaRequestDto request = new FacturaRequestDto();
        request.setUsuarioId(1);
        request.setMontoTotal(1500.0);
        request.setEstado("PAGADA");
        request.setFechaEmision("2025-06-01T10:15:30"); // fecha válida en formato ISO

        UsuarioDTO usuario = new UsuarioDTO();
        usuario.setId(1);
        when(usuarioClient.getUsuarioById(1)).thenReturn(Optional.of(usuario));

        Factura facturaGuardada = new Factura();
        facturaGuardada.setId(1L);
        facturaGuardada.setUsuarioId(1);
        facturaGuardada.setMontoTotal(1500.0);
        facturaGuardada.setEstado("PAGADA");
        facturaGuardada.setFechaEmision(LocalDateTime.parse("2025-06-01T10:15:30"));
        
        when(facturaRepository.save(any(Factura.class))).thenReturn(facturaGuardada);

      
        Factura resultado = facturaService.saveFactura(request);

        
        assertNotNull(resultado);
        assertEquals(1, resultado.getUsuarioId());
        assertEquals(1500.0, resultado.getMontoTotal());
        assertEquals("PAGADA", resultado.getEstado());
        assertEquals(LocalDateTime.parse("2025-06-01T10:15:30"), resultado.getFechaEmision());

        verify(usuarioClient).getUsuarioById(1);
        verify(facturaRepository).save(any(Factura.class));
    }

}
