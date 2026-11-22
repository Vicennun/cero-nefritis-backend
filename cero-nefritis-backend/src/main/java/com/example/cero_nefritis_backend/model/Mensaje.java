package com.example.cero_nefritis_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
@Data
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "remitente_id", nullable = false)
    private Usuario remitente;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    private Usuario destinatario;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    private String imagenUrl; // Para fotos adjuntas (mediciones/exámenes)
    private LocalDateTime fechaEnvio = LocalDateTime.now();
    private boolean leido = false;
}