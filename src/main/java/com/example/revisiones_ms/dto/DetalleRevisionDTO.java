package com.example.revisiones_ms.dto;

import com.example.revisiones_ms.clients.HabilitadorDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DetalleRevisionDTO {
    private Integer codigo;
    private String estadoCumplimiento;
    private String exception;
    private String observacion;
    private String estadoAuditoria;
    private String usuarioAuditoria;
    private OffsetDateTime fechaAuditoria;
    private Integer cabeceraId;
    private Integer habilitadorId;
    private HabilitadorDTO habilitador; // ✅ Información del habilitador obtenida del otro microservicio

    // 🔹 Constructor completo con `HabilitadorDTO`
    public DetalleRevisionDTO(Integer codigo, String estadoCumplimiento, String exception, String observacion, String estadoAuditoria,
                              String usuarioAuditoria, OffsetDateTime fechaAuditoria, Integer cabeceraId, Integer habilitadorId, HabilitadorDTO habilitador) {
        this.codigo = codigo;
        this.estadoCumplimiento = estadoCumplimiento;
        this.exception = exception;
        this.observacion = observacion;
        this.estadoAuditoria = estadoAuditoria;
        this.usuarioAuditoria = usuarioAuditoria;
        this.fechaAuditoria = fechaAuditoria;
        this.cabeceraId = cabeceraId;
        this.habilitadorId = habilitadorId;
        this.habilitador = habilitador;
    }

    // 🔹 Constructor adicional cuando solo se tiene `habilitadorId` (para POST y PUT)
    public DetalleRevisionDTO(Integer codigo, String estadoCumplimiento, String exception, String observacion, String estadoAuditoria,
                              String usuarioAuditoria, OffsetDateTime fechaAuditoria, Integer cabeceraId, Integer habilitadorId) {
        this.codigo = codigo;
        this.estadoCumplimiento = estadoCumplimiento;
        this.exception = exception;
        this.observacion = observacion;
        this.estadoAuditoria = estadoAuditoria;
        this.usuarioAuditoria = usuarioAuditoria;
        this.fechaAuditoria = fechaAuditoria;
        this.cabeceraId = cabeceraId;
        this.habilitadorId = habilitadorId;
        this.habilitador = null; // Se asignará posteriormente desde el microservicio de Habilitadores
    }
}
