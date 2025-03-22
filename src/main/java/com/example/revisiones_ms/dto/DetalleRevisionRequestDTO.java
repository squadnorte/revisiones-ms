package com.example.revisiones_ms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DetalleRevisionRequestDTO {
    private String estadoCumplimiento;
    private String exception;
    private String observacion;
    private String estadoAuditoria;
    private String usuarioAuditoria;
    private OffsetDateTime fechaAuditoria;
    private Integer cabeceraId;
    private Integer habilitadorId;

    public DetalleRevisionRequestDTO(String estadoCumplimiento, String exception, String observacion,
                                     String estadoAuditoria, String usuarioAuditoria,
                                     OffsetDateTime fechaAuditoria, Integer cabeceraId, Integer habilitadorId) {
        this.estadoCumplimiento = estadoCumplimiento;
        this.exception = exception;
        this.observacion = observacion;
        this.estadoAuditoria = estadoAuditoria;
        this.usuarioAuditoria = usuarioAuditoria;
        this.fechaAuditoria = fechaAuditoria;
        this.cabeceraId = cabeceraId;
        this.habilitadorId = habilitadorId;
    }
}
