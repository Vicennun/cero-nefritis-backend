package com.example.cero_nefritis_backend.controller;

import com.example.cero_nefritis_backend.model.Mensaje;
import com.example.cero_nefritis_backend.repository.MensajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeRepository mensajeRepo;

    @GetMapping("/conversacion")
    public List<Mensaje> obtenerConversacion(@RequestParam Long usuario1Id, @RequestParam Long usuario2Id) {
        return mensajeRepo.findByRemitenteIdAndDestinatarioIdOrRemitenteIdAndDestinatarioIdOrderByFechaEnvioAsc(
                usuario1Id, usuario2Id, usuario2Id, usuario1Id
        );
    }

    @PostMapping
    public Mensaje enviarMensaje(@RequestBody Mensaje mensaje) {
        return mensajeRepo.save(mensaje);
    }
}