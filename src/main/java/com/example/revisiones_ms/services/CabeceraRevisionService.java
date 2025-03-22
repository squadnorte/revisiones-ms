package com.example.revisiones_ms.services;

import com.example.revisiones_ms.dto.CabeceraRevisionDTO;
import com.example.revisiones_ms.dto.CabeceraRevisionRequestDTO;
import com.example.revisiones_ms.models.CabeceraRevision;
import com.example.revisiones_ms.repositories.CabeceraRevisionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CabeceraRevisionService {

    private final CabeceraRevisionRepository cabeceraRevisionRepository;

    public CabeceraRevisionService(CabeceraRevisionRepository cabeceraRevisionRepository) {
        this.cabeceraRevisionRepository = cabeceraRevisionRepository;
    }

    // 📌 Obtener todas las cabeceras activas
    public List<CabeceraRevisionDTO> obtenerTodas() {
        return cabeceraRevisionRepository.findByEstadoAuditoria("ACTIVO")
                .stream()
                .map(this::convertirADTO) // ✅ Se asegura que el método existe
                .collect(Collectors.toList());
    }

    // 📌 Obtener una cabecera por código
    public Optional<CabeceraRevisionDTO> obtenerPorCodigo(Integer codigo) {
        return cabeceraRevisionRepository.findById(codigo)
                .map(this::convertirADTO); // ✅ Se asegura que el método existe
    }

    // 📌 Crear una nueva cabecera (Recibe `CabeceraRevisionRequestDTO`)
    @Transactional
    public CabeceraRevisionDTO crearCabecera(CabeceraRevisionRequestDTO cabeceraRequest) {
        CabeceraRevision cabecera = convertirAEntidad(cabeceraRequest); // ✅ Se asegura que el método existe
        cabecera.setFechaAuditoria(OffsetDateTime.now());
        return convertirADTO(cabeceraRevisionRepository.save(cabecera));
    }

    // 📌 Actualizar una cabecera
    @Transactional
    public CabeceraRevisionDTO actualizarCabecera(Integer codigo, CabeceraRevisionRequestDTO cabeceraRequest) {
        CabeceraRevision cabeceraExistente = cabeceraRevisionRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Cabecera con código " + codigo + " no existe"));

        cabeceraExistente.setCodJira(cabeceraRequest.getCodJira());
        cabeceraExistente.setSquad(cabeceraRequest.getSquad());
        cabeceraExistente.setPo(cabeceraRequest.getPo());
        cabeceraExistente.setIniciativa(cabeceraRequest.getIniciativa());
        cabeceraExistente.setAnalistaSeguridad(cabeceraRequest.getAnalistaSeguridad());
        cabeceraExistente.setUsuarioAuditoria(cabeceraRequest.getUsuarioAuditoria());
        cabeceraExistente.setFechaAuditoria(OffsetDateTime.now());
        cabeceraExistente.setEstadoAuditoria(cabeceraRequest.getEstadoAuditoria());
        cabeceraExistente.setQuarter(cabeceraRequest.getQuarter());

        return convertirADTO(cabeceraRevisionRepository.save(cabeceraExistente));
    }

    // 📌 Eliminar una cabecera (Cambia de ACTIVO a INACTIVO) ✅ AGREGADO
    @Transactional
    public void eliminarCabecera(Integer codigo) {
        CabeceraRevision cabecera = cabeceraRevisionRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Cabecera con código " + codigo + " no encontrada"));

        cabecera.setEstadoAuditoria("INACTIVO");
        cabeceraRevisionRepository.save(cabecera);
    }

    // 🔹 Conversión de `CabeceraRevision` → `CabeceraRevisionDTO` ✅ AGREGADO
    private CabeceraRevisionDTO convertirADTO(CabeceraRevision cabecera) {
        return new CabeceraRevisionDTO(
                cabecera.getCodigo(),
                cabecera.getCodJira(),
                cabecera.getSquad(),
                cabecera.getPo(),
                cabecera.getIniciativa(),
                cabecera.getAnalistaSeguridad(),
                cabecera.getUsuarioAuditoria(),
                cabecera.getFechaAuditoria(),
                cabecera.getEstadoAuditoria(),
                cabecera.getQuarter(),
                List.of() // 🔹 No cargamos detalles aquí
        );
    }

    // 🔹 Conversión de `CabeceraRevisionRequestDTO` → `CabeceraRevision` ✅ AGREGADO
    private CabeceraRevision convertirAEntidad(CabeceraRevisionRequestDTO dto) {
        CabeceraRevision cabecera = new CabeceraRevision();
        cabecera.setCodJira(dto.getCodJira());
        cabecera.setSquad(dto.getSquad());
        cabecera.setPo(dto.getPo());
        cabecera.setIniciativa(dto.getIniciativa());
        cabecera.setAnalistaSeguridad(dto.getAnalistaSeguridad());
        cabecera.setUsuarioAuditoria(dto.getUsuarioAuditoria());
        cabecera.setFechaAuditoria(dto.getFechaAuditoria());
        cabecera.setEstadoAuditoria(dto.getEstadoAuditoria());
        cabecera.setQuarter(dto.getQuarter());
        return cabecera;
    }
}
