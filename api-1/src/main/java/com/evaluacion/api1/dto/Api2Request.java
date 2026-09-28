package com.evaluacion.api1.dto;

import java.math.BigDecimal;

public record Api2Request(
        String operacion,
        BigDecimal importe,
        String cliente,
        String secreto
) {}
