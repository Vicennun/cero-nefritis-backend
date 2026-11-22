package com.example.cero_nefritis_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "atenciones")
@Data
public class Atencion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoAtencion; // Ej: "0000360"

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Usuario paciente;

    @ManyToOne
    @JoinColumn(name = "profesional_id", nullable = false)
    private Usuario profesional;

    private String tipo;      // "consulta", "control", "procedimiento"
    private String titulo;    // Ej: "Control de seguimiento"
    private String modalidad; // "Videoconsulta", "Presencial"
    private String gravedad;  // "Extremo", "Alto", "Moderado", "Bajo"
    private String estado;    // "En proceso", "Completado", "Cancelado", "Reagendado"
    private LocalDateTime fechaHora;

    // Signos vitales (ProDetalleAtencion)
    private String temperatura;        // "38,6 °C"
    private String presionArterial;    // "128/82 mmHg"
    private String frecuenciaCardiaca; // "96 lpm"
    private String saturacion;         // "97 %"

    @Column(columnDefinition = "TEXT")
    private String motivoConsulta;

    @Column(columnDefinition = "TEXT")
    private String observacionesClinicas;

    @Column(columnDefinition = "TEXT")
    private String planIndicaciones;

    @ElementCollection
    private List<String> sintomasReportados;
}