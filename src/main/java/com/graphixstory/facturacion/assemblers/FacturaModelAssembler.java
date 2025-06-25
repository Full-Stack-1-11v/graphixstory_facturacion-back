package com.graphixstory.facturacion.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.graphixstory.facturacion.controller.FacturaController;
import com.graphixstory.facturacion.model.Factura;

@Component
public class FacturaModelAssembler implements RepresentationModelAssembler<Factura, EntityModel<Factura>>{
 @Override
    public EntityModel<Factura> toModel(Factura factura) {
        Link selfLink = linkTo(methodOn(FacturaController.class).getFacturaById(factura.getId()))
                .withSelfRel();

        Link allFacturasLink = linkTo(methodOn(FacturaController.class).getAllFacturas())
                .withRel("facturas");

        Link detalleLink = linkTo(methodOn(FacturaController.class).getFacturaConUsuario(factura.getId()))
                .withRel("detalle");

        return EntityModel.of(factura, selfLink, allFacturasLink, detalleLink);
    }
}
