package com.example.revisiones_ms.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DetalleRevisionBatchRequestDTO {
    private Integer cabeceraId;
    private List<Integer> habilitadoresIds; // Lista de habilitadores a agregar masivamente

    public DetalleRevisionBatchRequestDTO(Integer cabeceraId, List<Integer> habilitadoresIds) {
        this.cabeceraId = cabeceraId;
        this.habilitadoresIds = habilitadoresIds;
    }
}
