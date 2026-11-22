package com.example.cero_nefritis_backend.repository;
import com.example.cero_nefritis_backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {
    List<Recordatorio> findByPacienteIdAndActivoTrue(Long pacienteId);
}