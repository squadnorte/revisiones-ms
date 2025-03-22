package com.example.revisiones_ms.services;

import com.example.revisiones_ms.clients.HabilitadorClient;
import com.example.revisiones_ms.clients.HabilitadorDTO;
import com.example.revisiones_ms.dto.DetalleRevisionDTO;
import com.example.revisiones_ms.models.DetalleRevision;
import com.example.revisiones_ms.models.CabeceraRevision;
import com.example.revisiones_ms.repositories.DetalleRevisionRepository;
import com.example.revisiones_ms.repositories.CabeceraRevisionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetalleRevisionService {

    private static final Logger logger = LoggerFactory.getLogger(DetalleRevisionService.class);
    private final DetalleRevisionRepository detalleRevisionRepository;
    private final CabeceraRevisionRepository cabeceraRevisionRepository;
    private final HabilitadorClient habilitadorClient;

    public DetalleRevisionService(DetalleRevisionRepository detalleRevisionRepository,
                                  CabeceraRevisionRepository cabeceraRevisionRepository,HabilitadorClient habilitadorClient) {
        this.detalleRevisionRepository = detalleRevisionRepository;
        this.cabeceraRevisionRepository = cabeceraRevisionRepository;
        this.habilitadorClient = habilitadorClient;
    }

    // 📌 Listar todos los detalles activos de una cabecera
    public List<DetalleRevisionDTO> obtenerDetallesPorCabecera(Integer cabeceraId) {
        logger.info("🔍 Obteniendo detalles activos para la cabecera {}", cabeceraId);

        List<DetalleRevisionDTO> detalles = detalleRevisionRepository.findByCabeceraRevision_Codigo(cabeceraId)
                .stream()
                .filter(detalle -> "ACTIVO".equals(detalle.getEstadoAuditoria()))
                .map(this::convertirADTO)
                .collect(Collectors.toList());

        // 🔹 Obtener datos adicionales de los habilitadores
        for (DetalleRevisionDTO detalle : detalles) {
            if (detalle.getHabilitadorId() != null) {
                try {
                    HabilitadorDTO habilitador = habilitadorClient.obtenerHabilitadorPorCodigo(detalle.getHabilitadorId());
                    detalle.setHabilitador(habilitador);
                } catch (Exception e) {
                    logger.error("⚠ Error obteniendo información del habilitador ID {}: {}", detalle.getHabilitadorId(), e.getMessage());
                }
            }
        }

        return detalles;
    }

    // 📌 Agregar un detalle a una cabecera
    @Transactional
    public DetalleRevisionDTO agregarDetalle(DetalleRevisionDTO detalleDTO) {
        logger.info("Agregando un nuevo detalle para la cabecera {}", detalleDTO.getCabeceraId());

        CabeceraRevision cabecera = cabeceraRevisionRepository.findById(detalleDTO.getCabeceraId())
                .orElseThrow(() -> new RuntimeException("Cabecera no encontrada con ID: " + detalleDTO.getCabeceraId()));

        DetalleRevision detalle = convertirAEntidad(detalleDTO);
        detalle.setCabeceraRevision(cabecera);
        detalle.setFechaAuditoria(OffsetDateTime.now());

        DetalleRevision guardado = detalleRevisionRepository.save(detalle);
        return convertirADTO(guardado);
    }

    @Transactional
    public List<DetalleRevisionDTO> agregarDetallesMasivos(List<DetalleRevisionDTO> detalles) {
        logger.info("Agregando detalles masivos para la cabecera {}", detalles.get(0).getCabeceraId());

        CabeceraRevision cabecera = cabeceraRevisionRepository.findById(detalles.get(0).getCabeceraId())
                .orElseThrow(() -> new RuntimeException("Cabecera no encontrada con ID: " + detalles.get(0).getCabeceraId()));

        List<DetalleRevision> detallesEntidades = detalles.stream()
                .map(this::convertirAEntidad)
                .collect(Collectors.toList());

        detallesEntidades.forEach(detalle -> {
            detalle.setCabeceraRevision(cabecera);
            detalle.setFechaAuditoria(OffsetDateTime.now());
        });

        List<DetalleRevision> guardados = detalleRevisionRepository.saveAll(detallesEntidades);
        return guardados.stream().map(this::convertirADTO).collect(Collectors.toList());
    }


    // 📌 Eliminar un detalle (Cambia de ACTIVO a INACTIVO)
    @Transactional
    public void eliminarDetalle(Integer codigo) {
        logger.info("Cambiando estado a INACTIVO para el detalle con código {}", codigo);
        DetalleRevision detalle = detalleRevisionRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Detalle con código " + codigo + " no encontrado"));

        detalle.setEstadoAuditoria("INACTIVO");
        detalleRevisionRepository.save(detalle);
    }

    // 📌 Actualizar un detalle (Put)
    @Transactional
    public DetalleRevisionDTO actualizarDetalle(Integer codigo, DetalleRevisionDTO detalleDTO) {
        logger.info("Actualizando detalle con código {}", codigo);

        DetalleRevision detalleExistente = detalleRevisionRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Detalle con código " + codigo + " no encontrado"));

        detalleExistente.setEstadoCumplimiento(detalleDTO.getEstadoCumplimiento());
        detalleExistente.setException(detalleDTO.getException());
        detalleExistente.setObservacion(detalleDTO.getObservacion());
        detalleExistente.setEstadoAuditoria(detalleDTO.getEstadoAuditoria());
        detalleExistente.setUsuarioAuditoria(detalleDTO.getUsuarioAuditoria());
        detalleExistente.setFechaAuditoria(OffsetDateTime.now());

        DetalleRevision actualizado = detalleRevisionRepository.save(detalleExistente);
        return convertirADTO(actualizado);
    }

    // 🔹 Conversión de `DetalleRevision` → `DetalleRevisionDTO`
    private DetalleRevisionDTO convertirADTO(DetalleRevision detalle) {
        return new DetalleRevisionDTO(
                detalle.getCodigo(),
                detalle.getEstadoCumplimiento(),
                detalle.getException(),
                detalle.getObservacion(),
                detalle.getEstadoAuditoria(),
                detalle.getUsuarioAuditoria(),
                detalle.getFechaAuditoria(),
                detalle.getCabeceraRevision().getCodigo(), // ✅ Mantiene relación con CabeceraRevision
                detalle.getHabilitadorId()
        );
    }

    // 🔹 Conversión de `DetalleRevisionDTO` → `DetalleRevision`
    private DetalleRevision convertirAEntidad(DetalleRevisionDTO dto) {
        DetalleRevision detalle = new DetalleRevision();
        detalle.setCodigo(dto.getCodigo());
        detalle.setEstadoCumplimiento(dto.getEstadoCumplimiento());
        detalle.setException(dto.getException());
        detalle.setObservacion(dto.getObservacion());
        detalle.setEstadoAuditoria(dto.getEstadoAuditoria());
        detalle.setUsuarioAuditoria(dto.getUsuarioAuditoria());
        detalle.setHabilitadorId(dto.getHabilitadorId());
        return detalle;
    }

    // 📌 Obtener habilitadores NO asignados a una cabecera
    public List<HabilitadorDTO> obtenerHabilitadoresNoAsignados(Integer cabeceraId) {
        logger.info("🔍 Buscando habilitadores NO asignados para la cabecera {}", cabeceraId);

        // 🔹 Obtener los habilitadores asignados a la cabecera
        List<Integer> habilitadoresAsignados = detalleRevisionRepository.findByCabeceraRevision_Codigo(cabeceraId)
                .stream()
                .filter(detalle -> "ACTIVO".equals(detalle.getEstadoAuditoria()))
                .map(DetalleRevision::getHabilitadorId)
                .collect(Collectors.toList());


        logger.info("📌 Habilitadores ya asignados: {}", habilitadoresAsignados);

        // 🔹 Obtener todos los habilitadores disponibles desde el microservicio de habilitadores
        List<HabilitadorDTO> habilitadoresDisponibles = habilitadorClient.listarHabilitadores();

        // 🔹 Filtrar solo los habilitadores que NO están asignados
        List<HabilitadorDTO> habilitadoresNoAsignados = habilitadoresDisponibles.stream()
                .filter(habilitador -> !habilitadoresAsignados.contains(habilitador.getCodigo()))
                .collect(Collectors.toList());

        // 🔹 Obtener datos adicionales de cada habilitador
        for (HabilitadorDTO habilitador : habilitadoresNoAsignados) {
            try {
                HabilitadorDTO detallesHabilitador = habilitadorClient.obtenerHabilitadorPorCodigo(habilitador.getCodigo());
                habilitador.setDescripcion(detallesHabilitador.getDescripcion());
                habilitador.setObservacion(detallesHabilitador.getObservacion());

            } catch (Exception e) {
                logger.error("⚠ Error obteniendo información del habilitador ID {}: {}", habilitador.getCodigo(), e.getMessage());
            }
        }

        // 🔍 Verificamos qué habilitadores no asignados estamos devolviendo
        logger.info("✅ Habilitadores no asignados encontrados: {}", habilitadoresNoAsignados);

        return habilitadoresNoAsignados;
    }

}
