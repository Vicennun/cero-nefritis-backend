package com.example.cero_nefritis_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "seguimientos_activos")
@Data
public class SeguimientoActivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Usuario paciente;

    private String diagnostico;         // "Pielonefritis Aguda"
    private LocalDate fechaDiagnostico;
    private Integer diaActual;
    private Integer totalDias;
    private String antibioticoActual;   // "Ciprofloxacino 500mg"

    // Estado del Semáforo Clínico
    private String nivelSemaforo;      // "ROJO", "AMARILLO", "VERDE"
    private String tituloSemaforo;     // "Valoración presencial el mismo día"

    @Column(columnDefinition = "TEXT")
    private String mensajeProtocolo;

    @ElementCollection
    private List<String> triggersActivos; // ["Fiebre >= 38 °C", "Dolor lumbar 7/10"]

    // Últimas métricas del monitor
    private Double temperaturaActual;
    private Integer dolorLumbarActual;    // Escala 0-10
    private Integer glucosaCapilarActual; // mg/dL
    private Integer vasosHidratacion;
}