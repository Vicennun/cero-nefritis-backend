package com.example.cero_nefritis_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluaciones_riesgo")
@Data
public class EvaluacionRiesgo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Usuario paciente;

    private LocalDateTime fechaEvaluacion = LocalDateTime.now();

    // Factores Basales (Puntúan)
    private boolean edadMayor65;
    private boolean hombre;
    private boolean embarazo;
    private boolean diabetes;
    private boolean enfermedadRenalCronica;
    private boolean inmunosupresion;
    private boolean ituRecurrente;          // >=2 en 6 meses o >=3 en 12 meses
    private boolean pielonefritisPrevia;
    private boolean urosepsisPrevia;
    private boolean obstruccionOHidronefrosis;
    private boolean reflujoOVejigaNeurogena;
    private boolean litiasis;
    private boolean cateterPermanente;
    private boolean cateterismoIntermitente;
    private boolean resistenciaBlee;
    private boolean antibioticoUltimos30Dias;
    private boolean antibiotico31A90Dias;

    // Banderas Rojas / Signos de Alarma (Sobrescriben el puntaje)
    private boolean fiebreMayor38;
    private boolean escalofrios;
    private boolean dolorLumbarOFlanco;
    private boolean nauseasOVomitos;
    private boolean hipotensionOTaquicardia;
    private boolean alteracionEstadoMental;
    private boolean oliguria;

    // Resultados calculados
    private Integer puntajeBasal;
    private String nivelRiesgo; // BAJO, INTERMEDIO, ALTO, EXTREMO_URGENCIA
    private boolean banderaRojaActiva;
    private String recomendacion;

    // Estado de gestión clínica (para el panel del profesional)
    private String estadoAtencion = "EN_PROCESO"; // EN_PROCESO, COMPLETADO, CANCELADO
}