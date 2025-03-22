package com.example.revisiones_ms.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "cabecera_revision")
@Getter
@Setter
@NoArgsConstructor
public class CabeceraRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(name = "cod_jira", nullable = false, length = 50)
    private String codJira;

    @Column(name = "squad", nullable = false, length = 20)
    private String squad;

    @Column(name = "po", nullable = false, length = 100)
    private String po;

    @Column(name = "iniciativa", nullable = false, length = 500)
    private String iniciativa;

    @Column(name = "analista_seguridad", nullable = false, length = 50)
    private String analistaSeguridad;

    @Column(name = "usuario_auditoria", nullable = false, length = 50)
    private String usuarioAuditoria;

    @Column(name = "fecha_auditoria", nullable = false)
    private OffsetDateTime fechaAuditoria;

    @Column(name = "estado_auditoria", nullable = false, length = 20)
    private String estadoAuditoria;

    @Column(name = "quarter", length = 50)
    private String quarter;

    @OneToMany(mappedBy = "cabeceraRevision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleRevision> detalles;
}
