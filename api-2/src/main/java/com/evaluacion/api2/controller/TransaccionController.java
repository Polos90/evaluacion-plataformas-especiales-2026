package com.evaluacion.api2.controller;

import com.evaluacion.api2.dto.*;
import com.evaluacion.api2.entity.Transaccion;
import com.evaluacion.api2.service.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin(origins = "http://localhost:5173")
public class TransaccionController {

    private final TransaccionService service;

    public TransaccionController(TransaccionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OperacionResponse> crear(@Valid @RequestBody OperacionRequest request) {
        return ResponseEntity.ok(service.crear(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Transaccion> actualizarEstatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusPatchRequest request) {
        return ResponseEntity.ok(service.actualizarEstatus(id, request));
    }

    @GetMapping
    public Page<Transaccion> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return service.paginar(page, size, sortBy, direction);
    }
}
