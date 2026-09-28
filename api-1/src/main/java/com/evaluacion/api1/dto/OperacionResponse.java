package com.evaluacion.api1.dto;

public record OperacionResponse(
        Long id,
        String estatus,
        String referencia,
        String operacion
) {}
