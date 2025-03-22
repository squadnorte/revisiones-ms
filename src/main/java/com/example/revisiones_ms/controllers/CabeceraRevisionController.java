package com.example.revisiones_ms.controllers;

import com.example.revisiones_ms.dto.CabeceraRevisionDTO;
import com.example.revisiones_ms.dto.CabeceraRevisionRequestDTO;
import com.example.revisiones_ms.services.CabeceraRevisionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cabeceras")
@CrossOrigin(origins = "http://localhost:3000") // Permite solo este frontend
public class CabeceraRevisionController {

    private final CabeceraRevisionService cabeceraRevisionService;

    public CabeceraRevisionController(CabeceraRevisionService cabeceraRevisionService) {
        this.cabeceraRevisionService = cabeceraRevisionService;
    }

    // 📌 Obtener todas las cabeceras activas
    @GetMapping
    public ResponseEntity<List<CabeceraRevisionDTO>> obtenerTodas() {
        return ResponseEntity.ok(cabeceraRevisionService.obtenerTodas());
    }

    // 📌 Obtener una cabecera por código
    @GetMapping("/{codigo}")
    public ResponseEntity<CabeceraRevisionDTO> obtenerPorCodigo(@PathVariable Integer codigo) {
        return cabeceraRevisionService.obtenerPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 📌 Crear una nueva cabecera
    @PostMapping
    public ResponseEntity<CabeceraRevisionDTO> crearCabecera(@RequestBody CabeceraRevisionRequestDTO cabeceraRequest) {
        return ResponseEntity.ok(cabeceraRevisionService.crearCabecera(cabeceraRequest));
    }

    // 📌 Actualizar una cabecera
    @PutMapping("/{codigo}")
    public ResponseEntity<CabeceraRevisionDTO> actualizarCabecera(
            @PathVariable Integer codigo,
            @RequestBody CabeceraRevisionRequestDTO cabeceraRequest) {
        return ResponseEntity.ok(cabeceraRevisionService.actualizarCabecera(codigo, cabeceraRequest));
    }

    // 📌 Eliminar una cabecera (Cambia de ACTIVO a INACTIVO)
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminarCabecera(@PathVariable Integer codigo) {
        cabeceraRevisionService.eliminarCabecera(codigo); // ✅ Ahora existe el método
        return ResponseEntity.noContent().build();
    }
}
