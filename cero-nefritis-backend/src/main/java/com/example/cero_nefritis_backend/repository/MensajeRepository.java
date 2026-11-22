package com.example.cero_nefritis_backend.repository;
import com.example.cero_nefritis_backend.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByRemitenteIdAndDestinatarioIdOrRemitenteIdAndDestinatarioIdOrderByFechaEnvioAsc(
        Long rem1, Long dest1, Long rem2, Long dest2
    );
}