package com.example.cero_nefritis_backend.repository;

import com.example.cero_nefritis_backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EvaluacionRiesgoRepository extends JpaRepository<EvaluacionRiesgo, Long> {
    List<EvaluacionRiesgo> findByPacienteIdOrderByFechaEvaluacionDesc(Long pacienteId);
    List<EvaluacionRiesgo> findAllByOrderByFechaEvaluacionDesc();
}