package com.example.cero_nefritis_backend.controller;

import com.example.cero_nefritis_backend.model.PrevencionGuia;
import com.example.cero_nefritis_backend.repository.PrevencionGuiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prevenciones")
@RequiredArgsConstructor
public class PrevencionController {

    private final PrevencionGuiaRepository prevencionRepo;

    // Todas las guías (incluyendo borradores y en revisión) para el panel médico
    @GetMapping
    public List<PrevencionGuia> listarTodas() {
        return prevencionRepo.findAll();
    }

    // Solo guías aprobadas ("Activa") para la vista del paciente
    @GetMapping("/activas")
    public List<PrevencionGuia> listarActivas() {
        return prevencionRepo.findByEstadoOrderByFechaDesc("Activa");
    }

    @PostMapping
    public PrevencionGuia crearGuia(@RequestBody PrevencionGuia guia) {
        return prevencionRepo.save(guia);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<PrevencionGuia> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return prevencionRepo.findById(id).map(guia -> {
            guia.setEstado(estado);
            return ResponseEntity.ok(prevencionRepo.save(guia));
        }).orElse(ResponseEntity.notFound().build());
    }
}
