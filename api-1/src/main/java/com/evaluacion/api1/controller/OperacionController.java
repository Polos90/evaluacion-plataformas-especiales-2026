package com.evaluacion.api1.controller;

import com.evaluacion.api1.dto.OperacionRequest;
import com.evaluacion.api1.dto.OperacionResponse;
import com.evaluacion.api1.service.OperacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/operaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class OperacionController {

    private final OperacionService service;

    public OperacionController(OperacionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OperacionResponse> crear(@Valid @RequestBody OperacionRequest request) {
        return ResponseEntity.ok(service.procesar(request));
    }
}
