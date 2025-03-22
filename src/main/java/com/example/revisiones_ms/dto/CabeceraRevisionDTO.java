package com.example.revisiones_ms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CabeceraRevisionDTO {
    private Integer codigo;
    private String codJira;
    private String squad;
    private String po;
    private String iniciativa;
    private String analistaSeguridad;
    private String usuarioAuditoria;
    private OffsetDateTime fechaAuditoria;
    private String estadoAuditoria;
    private String quarter;
    private List<DetalleRevisionDTO> detalles; // ✅ Solo en `GET`

    public CabeceraRevisionDTO(Integer codigo, String codJira, String squad, String po, String iniciativa,
                               String analistaSeguridad, String usuarioAuditoria, OffsetDateTime fechaAuditoria,
                               String estadoAuditoria, String quarter, List<DetalleRevisionDTO> detalles) {
        this.codigo = codigo;
        this.codJira = codJira;
        this.squad = squad;
        this.po = po;
        this.iniciativa = iniciativa;
        this.analistaSeguridad = analistaSeguridad;
        this.usuarioAuditoria = usuarioAuditoria;
        this.fechaAuditoria = fechaAuditoria;
        this.estadoAuditoria = estadoAuditoria;
        this.quarter = quarter;
        this.detalles = detalles;
    }
}
