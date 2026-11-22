package com.example.cero_nefritis_backend.controller;
import com.example.cero_nefritis_backend.model.SeguimientoActivo;
import com.example.cero_nefritis_backend.repository.SeguimientoActivoRepository;
import com.example.cero_nefritis_backend.service.SeguimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/seguimiento")
@RequiredArgsConstructor
public class SeguimientoController {

    private final SeguimientoActivoRepository seguimientoRepo;
    private final SeguimientoService seguimientoService;

    // Lista todos los pacientes en seguimiento activo (Selector superior en ProSeguimientoActivo)
    @GetMapping
    public List<SeguimientoActivo> listarTodos() {
        return seguimientoRepo.findAll();
    }

    // Obtiene el seguimiento y estado del semáforo de un paciente específico
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<SeguimientoActivo> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return seguimientoRepo.findByPacienteId(pacienteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Recibe el check-in diario del paciente y recalcula el Semáforo Clínico
    @PostMapping("/{id}/checkin")
    public ResponseEntity<SeguimientoActivo> registrarCheckin(
            @PathVariable Long id,
            @RequestBody Map<String, Object> metricas) {

        return seguimientoRepo.findById(id).map(seg -> {
            if (metricas.get("temperatura") != null) {
                seg.setTemperaturaActual(Double.valueOf(metricas.get("temperatura").toString()));
            }
            if (metricas.get("dolorLumbar") != null) {
                seg.setDolorLumbarActual(Integer.valueOf(metricas.get("dolorLumbar").toString()));
            }
            if (metricas.get("glucosaCapilar") != null) {
                seg.setGlucosaCapilarActual(Integer.valueOf(metricas.get("glucosaCapilar").toString()));
            }
            if (metricas.get("vasosHidratacion") != null) {
                seg.setVasosHidratacion(Integer.valueOf(metricas.get("vasosHidratacion").toString()));
            }
            boolean esGestante = Boolean.parseBoolean(
                    metricas.getOrDefault("esGestante", false).toString()
            );

            SeguimientoActivo actualizado = seguimientoService.evaluarSemaforo(seg, esGestante);
            return ResponseEntity.ok(actualizado);
        }).orElse(ResponseEntity.notFound().build());
    }
}