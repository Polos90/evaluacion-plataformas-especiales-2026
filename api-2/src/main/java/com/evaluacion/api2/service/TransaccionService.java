package com.evaluacion.api2.service;

import com.evaluacion.api2.dto.OperacionRequest;
import com.evaluacion.api2.dto.OperacionResponse;
import com.evaluacion.api2.dto.StatusPatchRequest;
import com.evaluacion.api2.entity.Transaccion;
import com.evaluacion.api2.repository.TransaccionRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class TransaccionService {

    private final TransaccionRepository repository;

    public TransaccionService(TransaccionRepository repository) {
        this.repository = repository;
    }

    public OperacionResponse crear(OperacionRequest request) {
        Transaccion t = new Transaccion();
        t.setOperacion(request.getOperacion());
        t.setImporte(request.getImporte());
        t.setCliente(request.getCliente());
        t.setSecreto(request.getSecreto());
        t.setEstatus("Aprobada");
        t.setReferencia(generarReferencia());

        t = repository.save(t);

        return new OperacionResponse(
                t.getId(),
                t.getEstatus(),
                t.getReferencia(),
                t.getOperacion()
        );
    }

    public Transaccion actualizarEstatus(Long id, StatusPatchRequest request) {
        Transaccion t = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        if ("cancelar".equalsIgnoreCase(request.getEstatus())) {
            t.setEstatus("Cancelada");
        } else {
            throw new IllegalArgumentException("El estatus PATCH permitido es: cancelar");
        }

        return repository.save(t);
    }

    public Page<Transaccion> paginar(int page, int size, String sortBy, String direction) {
        Sort.Direction dir = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortBy));
        return repository.findAll(pageable);
    }

    private String generarReferencia() {
        String referencia;
        do {
            referencia = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
        } while (repository.existsByReferencia(referencia));
        return referencia;
    }
}
