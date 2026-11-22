package com.example.cero_nefritis_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "recordatorios")
@Data
public class Recordatorio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Usuario paciente;

    private String titulo;      // Ej: "Hidratación", "Temperatura"
    private String descripcion; // Ej: "Beber 1.5 a 2 litros de agua"
    private String frecuencia;  // "Diario", "Alarma", "Semanal"
    private boolean activo = true;
}