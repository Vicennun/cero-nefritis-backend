package com.example.cero_nefritis_backend.repository;

import com.example.cero_nefritis_backend.model.SeguimientoActivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SeguimientoActivoRepository extends JpaRepository<SeguimientoActivo, Long> {
    Optional<SeguimientoActivo> findByPacienteId(Long pacienteId);
}