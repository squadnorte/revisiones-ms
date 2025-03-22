package com.example.revisiones_ms.repositories;

import com.example.revisiones_ms.models.DetalleRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleRevisionRepository extends JpaRepository<DetalleRevision, Integer> {

    // ✅ Buscar detalles por ID de cabecera correctamente
    List<DetalleRevision> findByCabeceraRevision_Codigo(Integer cabeceraId);

    // 🔍 Buscar detalles por ID de habilitador
    List<DetalleRevision> findByHabilitadorId(Integer habilitadorId);

    // 🔍 Buscar detalles activos
    List<DetalleRevision> findByEstadoAuditoria(String estadoAuditoria);
}
