package com.example.cero_nefritis_backend.controller;
import com.example.cero_nefritis_backend.model.Atencion;
import com.example.cero_nefritis_backend.repository.AtencionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atenciones")
@RequiredArgsConstructor
public class AtencionController {

    private final AtencionRepository atencionRepo;

    @GetMapping("/paciente/{pacienteId}")
    public List<Atencion> listarPorPaciente(@PathVariable Long pacienteId) {
        return atencionRepo.findByPacienteIdOrderByFechaHoraDesc(pacienteId);
    }

    @GetMapping("/profesional/{profesionalId}")
    public List<Atencion> listarPorProfesional(@PathVariable Long profesionalId) {
        return atencionRepo.findByProfesionalIdOrderByFechaHoraDesc(profesionalId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Atencion> obtenerDetalle(@PathVariable Long id) {
        return atencionRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Atencion crearAtencion(@RequestBody Atencion atencion) {
        return atencionRepo.save(atencion);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Atencion> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return atencionRepo.findById(id).map(atencion -> {
            atencion.setEstado(estado);
            return ResponseEntity.ok(atencionRepo.save(atencion));
        }).orElse(ResponseEntity.notFound().build());
    }
}