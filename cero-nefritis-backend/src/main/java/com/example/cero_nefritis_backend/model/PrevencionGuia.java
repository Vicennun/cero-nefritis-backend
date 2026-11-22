package com.example.cero_nefritis_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "prevenciones_guia")
@Data
public class PrevencionGuia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String categoria;   // "Hábitos diarios", "Señales de alarma", "Salud sexual"
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String autor;       // "Dra. Valentina Rojas"
    private LocalDate fecha = LocalDate.now();
    private String estado;      // "Activa", "En revisión", "Borrador"
}