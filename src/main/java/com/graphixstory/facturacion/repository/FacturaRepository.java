package com.graphixstory.facturacion.repository;
import com.graphixstory.facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
