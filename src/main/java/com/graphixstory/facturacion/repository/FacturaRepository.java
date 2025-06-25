package com.graphixstory.facturacion.repository;
import com.graphixstory.facturacion.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Factura.
 * Proporciona métodos CRUD heredados de JpaRepository.
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
}
