package com.evaluacion.api2.dto;

public record OperacionResponse(
        Long id,
        String estatus,
        String referencia,
        String operacion
) {}
