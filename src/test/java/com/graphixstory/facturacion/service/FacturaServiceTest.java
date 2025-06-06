package com.graphixstory.facturacion.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.graphixstory.facturacion.client.UsuarioClient;
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

          // Configurar el mock para devolver una lista con esa factura
          when(facturaRepository.findAll()).thenReturn(List.of(factura));
  
          // Llamar al método de servicio
          List<Factura> facturas = facturaService.getAllFacturas();
  
          // Verificar que la lista no esté vacía y que contiene la factura correcta
          assertNotNull(facturas);
          assertEquals(1, facturas.size());
          assertEquals("1", facturas.get(0).getUsuarioId());
          assertEquals("250000", facturas.get(0).getMontoTotal());
          assertEquals("25/10/2024", facturas.get(0).getFechaEmision());
          assertEquals("PAGADA", facturas.get(0).getEstado());
  
          // Verificar que findAll() fue llamado en el repositorio
          verify(facturaRepository).findAll();

    }




}
