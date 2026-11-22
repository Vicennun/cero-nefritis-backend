package com.example.cero_nefritis_backend.controller;

import com.example.cero_nefritis_backend.model.EvaluacionRiesgo;
import com.example.cero_nefritis_backend.repository.EvaluacionRiesgoRepository;
import com.example.cero_nefritis_backend.service.RiesgoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionController {

    private final EvaluacionRiesgoRepository evaluacionRepo;
    private final RiesgoService riesgoService;

    // POST: Recibe el formulario del paciente, calcula el score y lo guarda
    @PostMapping
    public ResponseEntity<EvaluacionRiesgo> crearEvaluacion(@RequestBody EvaluacionRiesgo evaluacion) {
        EvaluacionRiesgo procesada = riesgoService.calcularYClasificar(evaluacion);
        return ResponseEntity.ok(evaluacionRepo.save(procesada));
    }

    // GET: Historial de evaluaciones de un paciente
    @GetMapping("/paciente/{pacienteId}")
    public List<EvaluacionRiesgo> obtenerPorPaciente(@PathVariable Long pacienteId) {
        return evaluacionRepo.findByPacienteIdOrderByFechaEvaluacionDesc(pacienteId);
    }

    // GET: Lista general para el Panel de Pacientes del Profesional (ProPacientes)
    @GetMapping("/panel-medico")
    public List<EvaluacionRiesgo> obtenerPanelMedico() {
        return evaluacionRepo.findAllByOrderByFechaEvaluacionDesc();
    }

    // PATCH: Actualizar estado de atención post-consulta (EN_PROCESO, COMPLETADO, CANCELADO)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<EvaluacionRiesgo> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return evaluacionRepo.findById(id).map(ev -> {
            ev.setEstadoAtencion(estado);
            return ResponseEntity.ok(evaluacionRepo.save(ev));
        }).orElse(ResponseEntity.notFound().build());
    }
}