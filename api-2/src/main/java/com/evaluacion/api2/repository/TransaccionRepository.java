package com.evaluacion.api2.repository;

import com.evaluacion.api2.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
    boolean existsByReferencia(String referencia);
}
