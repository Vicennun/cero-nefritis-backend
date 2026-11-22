package com.example.cero_nefritis_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "examenes_laboratorio")
@Data
public class ExamenLaboratorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Usuario paciente;

    private String nombreArchivo;      // "Urocultivo_Mateo_Octubre.pdf"
    private String urlArchivo;
    private LocalDateTime fechaSubida = LocalDateTime.now();
    private String estado = "Enviado"; // "Enviado", "Pendiente", "Revisado"

    // Campos digitalizados por el profesional (ProRevisionExamen)
    private String resultadoCultivo;   // "positivo", "negativo", "contaminado"
    private String microorganismo;     // "Escherichia coli"
    private String recuentoColonias;   // "> 100.000 UFC/mL"

    @Column(columnDefinition = "TEXT")
    private String resumenAntibiograma;

    private boolean resistenteATratamiento;
}