package com.example.revisiones_ms.clients;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HabilitadorDTO {
    private Integer codigo;          // 🔹 ID del habilitador
    private String titulo;           // 🔹 Título del habilitador
    private String descripcion;      // 🔹 Descripción del habilitador
    private Boolean imprescindible;  // 🔹 Indica si es imprescindible
    private String observacion;      // 🔹 Observaciones adicionales
    private String estadoAuditoria;  // 🔹 Estado (ACTIVO/INACTIVO)
    private Integer dimensionId;     // 🔹 Relación con la dimensión
    private Integer dominioId;       // 🔹 Relación con el dominio
    private Integer estadoAuditoriaId; // 🔹 Relación con estado auditoría
    private Integer imprescindibleId;  // 🔹 Relación con imprescindible
    private Integer lineamientoId;      // 🔹 Relación con lineamiento

}
