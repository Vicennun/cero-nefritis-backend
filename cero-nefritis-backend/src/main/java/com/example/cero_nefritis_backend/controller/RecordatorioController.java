package com.example.cero_nefritis_backend.controller;

import com.example.cero_nefritis_backend.model.Recordatorio;
import com.example.cero_nefritis_backend.repository.RecordatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recordatorios")
@RequiredArgsConstructor
public class RecordatorioController {

    private final RecordatorioRepository recordatorioRepo;

    @GetMapping("/paciente/{pacienteId}")
    public List<Recordatorio> listarActivos(@PathVariable Long pacienteId) {
        return recordatorioRepo.findByPacienteIdAndActivoTrue(pacienteId);
    }

    @PostMapping
    public Recordatorio crearRecordatorio(@RequestBody Recordatorio recordatorio) {
        return recordatorioRepo.save(recordatorio);
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<Recordatorio> marcarCompletado(@PathVariable Long id) {
        return recordatorioRepo.findById(id).map(rec -> {
            rec.setActivo(false);
            return ResponseEntity.ok(recordatorioRepo.save(rec));
        }).orElse(ResponseEntity.notFound().build());
    }
}