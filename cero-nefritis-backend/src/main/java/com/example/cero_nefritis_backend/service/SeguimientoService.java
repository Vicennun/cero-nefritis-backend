package com.example.cero_nefritis_backend.service;

import com.example.cero_nefritis_backend.model.ExamenLaboratorio;
import com.example.cero_nefritis_backend.model.SeguimientoActivo;
import com.example.cero_nefritis_backend.repository.ExamenLaboratorioRepository;
import com.example.cero_nefritis_backend.repository.SeguimientoActivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeguimientoService {

    private final SeguimientoActivoRepository seguimientoRepo;
    private final ExamenLaboratorioRepository examenRepo;

    // Recalcula el Semáforo Clínico al recibir check-in diario del paciente
    public SeguimientoActivo evaluarSemaforo(SeguimientoActivo seg, boolean esGestante) {
        List<String> triggers = new ArrayList<>();

        boolean rojoTemp = seg.getTemperaturaActual() != null && seg.getTemperaturaActual() >= 38.0;
        boolean rojoDolor = seg.getDolorLumbarActual() != null && seg.getDolorLumbarActual() >= 7;

        if (rojoTemp) triggers.add("Fiebre ≥ 38 °C (" + seg.getTemperaturaActual() + " °C)");
        if (rojoDolor) triggers.add("Dolor lumbar intenso (" + seg.getDolorLumbarActual() + "/10)");
        if (seg.getGlucosaCapilarActual() != null && seg.getGlucosaCapilarActual() >= 250) {
            triggers.add("Alerta Metabólica (" + seg.getGlucosaCapilarActual() + " mg/dL)");
        }

        if (rojoTemp || rojoDolor) {
            seg.setNivelSemaforo("ROJO");
            seg.setTituloSemaforo("Valoración presencial el mismo día");
            seg.setMensajeProtocolo("El paciente presenta criterios rojos activos. Se requiere valoración presencial el mismo día.");
        } else if ((seg.getTemperaturaActual() != null && seg.getTemperaturaActual() >= 37.5)
                || (seg.getDolorLumbarActual() != null && seg.getDolorLumbarActual() >= 4)
                || esGestante) {
            if (esGestante) triggers.add("Gestante (Prioridad obstétrica)");
            seg.setNivelSemaforo("AMARILLO");
            seg.setTituloSemaforo("Valoración Pronta / Vigilar evolución");
            seg.setMensajeProtocolo("Síntomas moderados o factor modificador activo. Mantener vigilancia estrecha.");
        } else {
            triggers.add("Apirética");
            triggers.add("Sin dolor lumbar");
            seg.setNivelSemaforo("VERDE");
            seg.setTituloSemaforo("Sin señales de alarma");
            seg.setMensajeProtocolo("Evolución favorable dentro de parámetros esperados.");
        }

        seg.setTriggersActivos(triggers);
        return seguimientoRepo.save(seg);
    }

    // Guarda revisión de urocultivo y escala a ROJO si hay resistencia al antibiótico
    public ExamenLaboratorio revisarExamen(Long examenId, ExamenLaboratorio datosRevision) {
        ExamenLaboratorio examen = examenRepo.findById(examenId).orElseThrow();
        examen.setResultadoCultivo(datosRevision.getResultadoCultivo());
        examen.setMicroorganismo(datosRevision.getMicroorganismo());
        examen.setRecuentoColonias(datosRevision.getRecuentoColonias());
        examen.setResumenAntibiograma(datosRevision.getResumenAntibiograma());
        examen.setEstado("Revisado");

        // Verificar si el antibiograma reporta resistencia al antibiótico actual del paciente
        seguimientoRepo.findByPacienteId(examen.getPaciente().getId()).ifPresent(seg -> {
            String abActual = seg.getAntibioticoActual().split(" ")[0].toLowerCase();
            if (datosRevision.getResumenAntibiograma() != null &&
                datosRevision.getResumenAntibiograma().toLowerCase().contains("resistente: " + abActual)) {
                examen.setResistenteATratamiento(true);
                seg.setNivelSemaforo("ROJO");
                seg.setTituloSemaforo("Resistencia antibiótica detectada");
                seg.getTriggersActivos().add("Resistencia a " + seg.getAntibioticoActual());
                seguimientoRepo.save(seg);
            }
        });

        return examenRepo.save(examen);
    }
}