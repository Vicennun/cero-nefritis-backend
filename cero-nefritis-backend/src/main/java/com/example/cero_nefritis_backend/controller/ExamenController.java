package com.example.cero_nefritis_backend.controller;

import com.example.cero_nefritis_backend.model.ExamenLaboratorio;
import com.example.cero_nefritis_backend.model.Usuario;
import com.example.cero_nefritis_backend.repository.ExamenLaboratorioRepository;
import com.example.cero_nefritis_backend.repository.UsuarioRepository;
import com.example.cero_nefritis_backend.service.SeguimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/examenes")
@RequiredArgsConstructor
public class ExamenController {

    private final ExamenLaboratorioRepository examenRepo;
    private final UsuarioRepository usuarioRepo;
    private final SeguimientoService seguimientoService;

    @GetMapping("/paciente/{pacienteId}")
    public List<ExamenLaboratorio> listarPorPaciente(@PathVariable Long pacienteId) {
        return examenRepo.findByPacienteIdOrderByFechaSubidaDesc(pacienteId);
    }

    @GetMapping("/pendientes")
    public List<ExamenLaboratorio> listarPendientes() {
        return examenRepo.findByEstadoOrderByFechaSubidaDesc("Enviado");
    }

    // Sube el archivo del examen (PDF/JPG/PNG) desde PatientExamenes
    @PostMapping("/subir")
    public ResponseEntity<ExamenLaboratorio> subirExamen(
            @RequestParam("pacienteId") Long pacienteId,
            @RequestParam("file") MultipartFile file) throws IOException {

        Usuario paciente = usuarioRepo.findById(pacienteId).orElseThrow();

        Path directorio = Paths.get("uploads");
        if (!Files.exists(directorio)) {
            Files.createDirectories(directorio);
        }
        String nombreLimpio = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path rutaArchivo = directorio.resolve(nombreLimpio);
        Files.write(rutaArchivo, file.getBytes());

        ExamenLaboratorio examen = new ExamenLaboratorio();
        examen.setPaciente(paciente);
        examen.setNombreArchivo(file.getOriginalFilename());
        examen.setUrlArchivo("/uploads/" + nombreLimpio);
        examen.setEstado("Enviado");

        return ResponseEntity.ok(examenRepo.save(examen));
    }

    // Guarda la transcripción clínica del médico y escala el semáforo si hay resistencia
    @PutMapping("/{id}/revisar")
    public ResponseEntity<ExamenLaboratorio> revisarExamen(
            @PathVariable Long id,
            @RequestBody ExamenLaboratorio datosRevision) {
        return ResponseEntity.ok(seguimientoService.revisarExamen(id, datosRevision));
    }
}