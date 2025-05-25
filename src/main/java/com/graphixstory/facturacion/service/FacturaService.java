package com.graphixstory.facturacion.service;

import org.springframework.stereotype.Service;

import com.graphixstory.facturacion.client.UsuarioClient;
import com.graphixstory.facturacion.dto.FacturaUsuarioDto;
import com.graphixstory.facturacion.dto.UsuarioDTO;
import com.graphixstory.facturacion.model.Factura;
import com.graphixstory.facturacion.repository.FacturaRepository;
import java.util.List;
import java.util.Optional;


@Service
public class FacturaService {

 private final FacturaRepository facturaRepository;
 private final UsuarioClient usuarioClient;

    public FacturaService(FacturaRepository facturaRepository, UsuarioClient usuarioClient) {
        this.facturaRepository = facturaRepository;
        this.usuarioClient = usuarioClient;
    }

    public List<Factura> getAllFacturas() {
        return facturaRepository.findAll();
    }

    public Optional<Factura> getFacturaById(Long id) {
        return facturaRepository.findById(id);
    }

    public Factura saveFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    public void deleteFactura(Long id) {
        facturaRepository.deleteById(id);
    }
     public Optional<FacturaUsuarioDto> getFacturaConUsuarioById(Long id) {
        Optional<Factura> facturaOpt = facturaRepository.findById(id);
        if (facturaOpt.isEmpty()) {
            return Optional.empty();
        }

        Factura factura = facturaOpt.get();
        // aqui llamamos a la api de usuario para obtener los datos
        UsuarioDTO usuario = usuarioClient.getUsuarioById(factura.getUsuarioId());

        // el DTO combinando datos
        FacturaUsuarioDto dto = new FacturaUsuarioDto();
        dto.setId(factura.getId());
        dto.setMontoTotal(factura.getMontoTotal());
        dto.setEstado(factura.getEstado());
        dto.setFechaEmision(factura.getFechaEmision().toString());
        dto.setUsuarioId(usuario.getId());
        dto.setNombreUsuario(usuario.getNombre());
     

        return Optional.of(dto);
    }
}
