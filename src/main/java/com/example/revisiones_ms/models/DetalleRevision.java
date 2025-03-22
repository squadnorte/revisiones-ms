package com.example.revisiones_ms.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;

@Entity
@Table(name = "detalle_revision")
@Getter
@Setter
@NoArgsConstructor
public class DetalleRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name = "estado_cumplimiento", nullable = false, length = 50)
    private String estadoCumplimiento;

    @Column(name = "exception", length = 50)
    private String exception;

    @Column(name = "observacion", columnDefinition = "nvarchar(max)")
    private String observacion;

    @Column(name = "estado_auditoria", nullable = false, length = 20)
    private String estadoAuditoria;

    @Column(name = "usuario_auditoria", nullable = false, length = 50)
    private String usuarioAuditoria;

    @Column(name = "fecha_auditoria", nullable = false)
    private OffsetDateTime fechaAuditoria;

    @ManyToOne
    @JoinColumn(name = "cabecera_id", nullable = false)
    private CabeceraRevision cabeceraRevision;

    @Column(name = "habilitador_id", nullable = false)
    private Integer habilitadorId;
}
