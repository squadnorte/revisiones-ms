package com.example.revisiones_ms.clients;

import com.example.revisiones_ms.clients.HabilitadorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "habilitador-client", url = "http://localhost:8082/habilitadores")
public interface HabilitadorClient {

    // 📌 Obtener TODOS los habilitadores
    @GetMapping
    List<HabilitadorDTO> listarHabilitadores();

    // 📌 Obtener un habilitador por código
    @GetMapping("/{codigo}")
    HabilitadorDTO obtenerHabilitadorPorCodigo(@PathVariable Integer codigo);
}

