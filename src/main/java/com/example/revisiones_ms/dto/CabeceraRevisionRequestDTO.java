package com.example.revisiones_ms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CabeceraRevisionRequestDTO {
    private String codJira;
    private String squad;
    private String po;
    private String iniciativa;
    private String analistaSeguridad;
    private String usuarioAuditoria;
    private OffsetDateTime fechaAuditoria;
    private String estadoAuditoria;
    private String quarter;

    public CabeceraRevisionRequestDTO(String codJira, String squad, String po, String iniciativa,
                                      String analistaSeguridad, String usuarioAuditoria,
                                      OffsetDateTime fechaAuditoria, String estadoAuditoria, String quarter) {
        this.codJira = codJira;
        this.squad = squad;
        this.po = po;
        this.iniciativa = iniciativa;
        this.analistaSeguridad = analistaSeguridad;
        this.usuarioAuditoria = usuarioAuditoria;
        this.fechaAuditoria = fechaAuditoria;
        this.estadoAuditoria = estadoAuditoria;
        this.quarter = quarter;
    }
}
