package com.example.cero_nefritis_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCompleto;

    @Column(unique = true, nullable = false)
    private String rut;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private Integer edad;
    private String sexo;
    private String telefono;

    @Enumerated(EnumType.STRING)
    private Rol rol; // PACIENTE, PROFESIONAL, ADMIN, DESARROLLADOR

    private String especialidad; // Solo para PROFESIONAL (ej. Médico tratante)
    private boolean consentimientoInformado; // Ley 19.628 / 20.584
}