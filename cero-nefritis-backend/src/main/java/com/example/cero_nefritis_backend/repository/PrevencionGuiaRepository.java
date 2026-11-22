package com.example.cero_nefritis_backend.repository;
import com.example.cero_nefritis_backend.model.PrevencionGuia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrevencionGuiaRepository extends JpaRepository<PrevencionGuia, Long> {
    List<PrevencionGuia> findByEstadoOrderByFechaDesc(String estado);
}