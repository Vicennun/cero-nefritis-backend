package com.example.cero_nefritis_backend.service;

import com.example.cero_nefritis_backend.model.EvaluacionRiesgo;
import org.springframework.stereotype.Service;

@Service
public class RiesgoService {

    public EvaluacionRiesgo calcularYClasificar(EvaluacionRiesgo ev) {
        // 1. Verificar Banderas Rojas (Sobrescriben cualquier puntaje basal)
        boolean tieneAlarma = ev.isFiebreMayor38() || ev.isEscalofrios() ||
                              ev.isDolorLumbarOFlanco() || ev.isNauseasOVomitos() ||
                              ev.isHipotensionOTaquicardia() || ev.isAlteracionEstadoMental() ||
                              ev.isOliguria();

        // 2. Calcular Puntaje Basal de Susceptibilidad
        int puntos = 0;
        if (ev.isEdadMayor65() || ev.isHombre()) puntos += 1;
        if (ev.isEmbarazo() || ev.isDiabetes() || ev.isEnfermedadRenalCronica() || ev.isInmunosupresion()) puntos += 2;
        if (ev.isItuRecurrente() || ev.isPielonefritisPrevia()) puntos += 2;
        if (ev.isUrosepsisPrevia()) puntos += 3;
        if (ev.isObstruccionOHidronefrosis()) puntos += 3;
        if (ev.isReflujoOVejigaNeurogena()) puntos += 2;
        if (ev.isLitiasis()) puntos += 1;
        if (ev.isCateterPermanente()) puntos += 2;
        if (ev.isCateterismoIntermitente()) puntos += 1;
        if (ev.isResistenciaBlee()) puntos += 3;
        if (ev.isAntibioticoUltimos30Dias()) puntos += 2;
        if (ev.isAntibiotico31A90Dias()) puntos += 1;

        ev.setPuntajeBasal(puntos);
        ev.setBanderaRojaActiva(tieneAlarma);

        // 3. Clasificación Final
        if (tieneAlarma) {
            ev.setNivelRiesgo("EXTREMO_URGENCIA");
            ev.setRecomendacion("ALERTA CRÍTICA: Presentas signos de alarma compatibles con complicación renal. Acude a un servicio de urgencia médica hoy mismo.");
        } else if (puntos >= 8) {
            ev.setNivelRiesgo("ALTO");
            ev.setRecomendacion("Riesgo Alto de complicación: Se ha notificado a tu profesional asignado para revisión prioritaria. Mantén hidratación y vigila temperatura.");
        } else if (puntos >= 4) {
            ev.setNivelRiesgo("INTERMEDIO");
            ev.setRecomendacion("Riesgo Intermedio: Mantén control de comorbilidades, registra tu hidratación diaria y consulta precozmente ante ardor al orinar.");
        } else {
            ev.setNivelRiesgo("BAJO");
            ev.setRecomendacion("Riesgo Bajo: Continúa con hábitos preventivos (hidratación 1.5-2L, micción frecuente y postcoital).");
        }

        return ev;
    }
}