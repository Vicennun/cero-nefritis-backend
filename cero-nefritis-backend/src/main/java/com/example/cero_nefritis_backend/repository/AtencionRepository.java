package com.example.cero_nefritis_backend.repository;

import com.example.cero_nefritis_backend.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AtencionRepository extends JpaRepository<Atencion, Long> {
    List<Atencion> findByPacienteIdOrderByFechaHoraDesc(Long pacienteId);
    List<Atencion> findByProfesionalIdOrderByFechaHoraDesc(Long profesionalId);
}