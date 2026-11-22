package com.example.cero_nefritis_backend.repository;

import com.example.cero_nefritis_backend.model.ExamenLaboratorio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamenLaboratorioRepository extends JpaRepository<ExamenLaboratorio, Long> {
    List<ExamenLaboratorio> findByPacienteIdOrderByFechaSubidaDesc(Long pacienteId);
    List<ExamenLaboratorio> findByEstadoOrderByFechaSubidaDesc(String estado);
}