package com.graphixstory.facturacion.service;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphixstory.facturacion.client.UsuarioClient;
import com.graphixstory.facturacion.dto.FacturaUsuarioDto;
import com.graphixstory.facturacion.dto.UsuarioDTO;
import com.graphixstory.facturacion.dto.FacturaRequestDto;
import com.graphixstory.facturacion.model.Factura;
import com.graphixstory.facturacion.repository.FacturaRepository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Servicio que gestiona la lógica de negocio relacionada con las facturas.
 * Permite crear, obtener y eliminar facturas, además de consultar datos combinados con usuario.
 */
@Service
public class FacturaService {

    private static final Logger logger = LoggerFactory.getLogger(FacturaService.class);

    private final FacturaRepository facturaRepository;
    private final UsuarioClient usuarioClient;

    /**
     * Constructor que inyecta dependencias del repositorio de facturas y el cliente de usuarios.
     *
     * @param facturaRepository Repositorio para operaciones CRUD sobre facturas
     * @param usuarioClient Cliente REST para obtener datos del usuario
     */
    public FacturaService(FacturaRepository facturaRepository, UsuarioClient usuarioClient) {
        this.facturaRepository = facturaRepository;
        this.usuarioClient = usuarioClient;
    }

    /**
     * Obtiene la lista de todas las facturas registradas.
     *
     * @return Lista de facturas
     */
    public List<Factura> getAllFacturas() {
        logger.info("Obteniendo todas las facturas");
        return facturaRepository.findAll();
    }

    /**
     * Busca una factura por su ID.
     *
     * @param id Identificador de la factura
     * @return Optional con la factura si existe
     */
    public Optional<Factura> getFacturaById(Long id) {
        logger.info("Buscando factura con ID: {}", id);
        return facturaRepository.findById(id);
    }

     /**
     * Guarda una nueva factura en la base de datos a partir de un DTO de entrada.
     * Valida la existencia del usuario y el formato de la fecha.
     *
     * @param facturaRequestDto DTO con los datos de la factura a crear
     * @return Factura creada y guardada
     * @throws IllegalArgumentException si faltan datos requeridos o la fecha tiene formato inválido
     */
    public Factura saveFactura(FacturaRequestDto facturaRequestDto) {
        logger.info("Intentando crear una nueva factura para el usuario con ID: {}", facturaRequestDto.getUsuarioId());

        if (facturaRequestDto.getUsuarioId() == null) {
            logger.error("ID de usuario es nulo");
            throw new IllegalArgumentException("El ID de usuario es obligatorio para crear una factura.");
        }

        Optional<UsuarioDTO> usuarioOpt = usuarioClient.getUsuarioById(facturaRequestDto.getUsuarioId());

        if (usuarioOpt.isEmpty()) {
            logger.error("Usuario con ID {} no encontrado", facturaRequestDto.getUsuarioId());
            throw new IllegalArgumentException("Usuario con ID " + facturaRequestDto.getUsuarioId() + " no encontrado en el servicio de usuarios.");
        }

        Factura nuevaFactura = new Factura();
        nuevaFactura.setMontoTotal(facturaRequestDto.getMontoTotal());
        nuevaFactura.setEstado(facturaRequestDto.getEstado());
        
        if (facturaRequestDto.getFechaEmision() != null && !facturaRequestDto.getFechaEmision().isEmpty()) {
            try {
                nuevaFactura.setFechaEmision(LocalDateTime.parse(facturaRequestDto.getFechaEmision()));
            } catch (DateTimeParseException e) {
                logger.error("Error al parsear la fecha de emisión: {}", facturaRequestDto.getFechaEmision());
                throw new IllegalArgumentException("Formato de fecha de emisión inválido. Se espera ISO 8601 (ej. 2025-05-25T15:30:00).");
            }
        } else {
            nuevaFactura.setFechaEmision(LocalDateTime.now());
        }

        nuevaFactura.setUsuarioId(facturaRequestDto.getUsuarioId());

        Factura facturaGuardada = facturaRepository.save(nuevaFactura);
        logger.info("Factura creada exitosamente con ID: {}", facturaGuardada.getId());
        
        return facturaGuardada;
    }



      /**
     * Elimina una factura por su ID.
     *
     * @param id Identificador de la factura a eliminar
     */
    public void deleteFactura(Long id) {
        logger.info("Eliminando factura con ID: {}", id);
        facturaRepository.deleteById(id);
    }

    /**
     * Obtiene los datos de una factura junto con la información del usuario asociado.
     *
     * @param id ID de la factura
     * @return Optional con el DTO combinado, o vacío si no se encuentra la factura o el usuario
     */
    public Optional<FacturaUsuarioDto> getFacturaConUsuarioById(Long id) {
        logger.info("Buscando factura con detalles de usuario para ID: {}", id);
        Optional<Factura> facturaOpt = facturaRepository.findById(id);
        if (facturaOpt.isEmpty()) {
            logger.warn("Factura con ID {} no encontrada", id);
            return Optional.empty();
        }

        Factura factura = facturaOpt.get();
        Optional<UsuarioDTO> usuarioDtoOpt = usuarioClient.getUsuarioById(factura.getUsuarioId());

        if (usuarioDtoOpt.isPresent()) {
            logger.info("Usuario encontrado para factura ID: {}", id);
            UsuarioDTO usuario = usuarioDtoOpt.get();
            FacturaUsuarioDto dto = new FacturaUsuarioDto(factura, usuario);
            return Optional.of(dto);
        } else {
            logger.warn("Usuario con ID {} para factura {} no encontrado", factura.getUsuarioId(), id);
            return Optional.empty();
        }
    }
}