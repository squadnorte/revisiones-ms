package com.example.revisiones_ms.repositories;

import com.example.revisiones_ms.models.CabeceraRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabeceraRevisionRepository extends JpaRepository<CabeceraRevision, Integer> {

    // 🔍 Buscar todas las cabeceras activas
    List<CabeceraRevision> findByEstadoAuditoria(String estadoAuditoria);

    // 🔍 Buscar cabeceras por código de Jira


    List<CabeceraRevision> findByCodJiraContainingIgnoreCase(String codJira);

    // 🔍 Buscar por Squad
    List<CabeceraRevision> findBySquadContainingIgnoreCase(String squad);

    // 🔍 Buscar por estado
    List<CabeceraRevision> findByEstadoAuditoriaIgnoreCase(String estadoAuditoria);
}
