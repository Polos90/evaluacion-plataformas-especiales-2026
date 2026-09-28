package com.evaluacion.api2.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class OperacionRequest {

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")
    @Size(max = 30)
    private String operacion;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal importe;

    @NotBlank
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$")
    @Size(max = 100)
    private String cliente;

    @NotBlank
    private String secreto;

    public String getOperacion() { return operacion; }
    public void setOperacion(String operacion) { this.operacion = operacion; }
    public BigDecimal getImporte() { return importe; }
    public void setImporte(BigDecimal importe) { this.importe = importe; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public String getSecreto() { return secreto; }
    public void setSecreto(String secreto) { this.secreto = secreto; }
}
