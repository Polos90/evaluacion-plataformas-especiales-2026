package com.evaluacion.api2.dto;

import jakarta.validation.constraints.NotBlank;

public class StatusPatchRequest {

    @NotBlank
    private String estatus;

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
}
