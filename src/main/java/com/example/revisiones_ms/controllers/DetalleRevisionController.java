package com.example.revisiones_ms.controllers;

import com.example.revisiones_ms.clients.HabilitadorDTO;
import com.example.revisiones_ms.dto.DetalleRevisionDTO;
import com.example.revisiones_ms.dto.DetalleRevisionRequestDTO;
import com.example.revisiones_ms.dto.DetalleRevisionBatchRequestDTO;
import com.example.revisiones_ms.services.DetalleRevisionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detalles")
@CrossOrigin(origins = "http://localhost:3000") // Permite solo este frontend
public class DetalleRevisionController {

    private static final Logger logger = LoggerFactory.getLogger(DetalleRevisionController.class);
    private final DetalleRevisionService detalleRevisionService;

    public DetalleRevisionController(DetalleRevisionService detalleRevisionService) {
        this.detalleRevisionService = detalleRevisionService;
    }

    // 📌 Obtener detalles por ID de cabecera
    @GetMapping("/cabecera/{cabeceraId}")
    public ResponseEntity<List<DetalleRevisionDTO>> obtenerDetallesPorCabecera(@PathVariable Integer cabeceraId) {
        logger.info("Obteniendo detalles de la cabecera con ID {}", cabeceraId);
        return ResponseEntity.ok(detalleRevisionService.obtenerDetallesPorCabecera(cabeceraId));
    }

    // 📌 Agregar un detalle
    @PostMapping
    public ResponseEntity<DetalleRevisionDTO> agregarDetalle(@RequestBody DetalleRevisionRequestDTO detalleRequestDTO) {
        logger.info("Recibiendo solicitud para agregar un detalle.");
        DetalleRevisionDTO detalleDTO = convertirARequestDTO(detalleRequestDTO);
        return ResponseEntity.ok(detalleRevisionService.agregarDetalle(detalleDTO));
    }

    // 📌 Actualizar un detalle (PUT)
    // 📌 Actualizar un detalle
    @PutMapping("/{codigo}")
    public ResponseEntity<DetalleRevisionDTO> actualizarDetalle(@PathVariable Integer codigo,
                                                                @RequestBody DetalleRevisionRequestDTO detalleRequestDTO) {
        logger.info("Recibiendo solicitud para actualizar detalle con código {}", codigo);
        DetalleRevisionDTO detalleDTO = convertirARequestDTO(detalleRequestDTO);
        return ResponseEntity.ok(detalleRevisionService.actualizarDetalle(codigo, detalleDTO));
    }

    @PostMapping("/masivo")
    public ResponseEntity<?> agregarDetallesMasivos(@RequestBody DetalleRevisionBatchRequestDTO batchRequest) {
        logger.info("Agregando detalles masivos para la cabecera {}", batchRequest.getCabeceraId());

        List<DetalleRevisionDTO> detallesDTO = batchRequest.getHabilitadoresIds().stream()
                .map(habilitadorId -> new DetalleRevisionDTO(
                        null,
                        "NO CUMPLE",
                        null,
                        null,
                        "ACTIVO",
                        "Sistema",
                        OffsetDateTime.now(),
                        batchRequest.getCabeceraId(),
                        habilitadorId,
                        null
                ))
                .collect(Collectors.toList());

        try {
            List<DetalleRevisionDTO> resultado = detalleRevisionService.agregarDetallesMasivos(detallesDTO);
            return ResponseEntity.ok(resultado);
        } catch (DataIntegrityViolationException e) {
            // Revisa si es clave duplicada (por índice único compuesto)
            if (e.getMessage() != null && e.getMessage().contains("UQ_HabilitadorPorCabecera_Activo")) {
                logger.warn("Ya existen algunos habilitadores para la cabecera {}", batchRequest.getCabeceraId());
                return ResponseEntity.badRequest().body("⚠️ Algunos habilitadores ya están registrados para esta cabecera.");
            }

            logger.error("❌ Error inesperado al guardar detalles masivos", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Error al guardar habilitadores.");
        }
    }



    // 📌 Eliminar un detalle (Cambio de estado a INACTIVO)
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminarDetalle(@PathVariable Integer codigo) {
        logger.info("Eliminando (cambiando a inactivo) el detalle con código {}", codigo);
        detalleRevisionService.eliminarDetalle(codigo);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Conversión de `DetalleRevisionRequestDTO` a `DetalleRevisionDTO`
    private DetalleRevisionDTO convertirARequestDTO(DetalleRevisionRequestDTO requestDTO) {
        return new DetalleRevisionDTO(
                null, // Código generado automáticamente
                requestDTO.getEstadoCumplimiento(),
                requestDTO.getException(),
                requestDTO.getObservacion(),
                requestDTO.getEstadoAuditoria(),
                requestDTO.getUsuarioAuditoria(),
                OffsetDateTime.now(), // Fecha de auditoría actual
                requestDTO.getCabeceraId(),
                requestDTO.getHabilitadorId(),
                null // No pasamos el `habilitador` aquí
        );
    }

    // 📌 Obtener habilitadores NO asignados a una cabecera
    @GetMapping("/cabecera/{cabeceraId}/noasignados")
    public ResponseEntity<List<HabilitadorDTO>> obtenerHabilitadoresNoAsignados(@PathVariable Integer cabeceraId) {
        logger.info("Obteniendo habilitadores no asignados para la cabecera {}", cabeceraId);
        return ResponseEntity.ok(detalleRevisionService.obtenerHabilitadoresNoAsignados(cabeceraId));
    }


}
