package com.evaluacion.api1.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class OperacionRequest {

    @NotBlank(message = "operacion es obligatoria")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "operacion solo acepta caracteres")
    @Size(max = 30)
    private String operacion;

    @NotNull(message = "importe es obligatorio")
    @DecimalMin(value = "0.01", message = "importe debe ser mayor a 0")
    private BigDecimal importe;

    @NotBlank(message = "cliente es obligatorio")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$", message = "cliente solo acepta caracteres")
    @Size(max = 100)
    private String cliente;

    @NotBlank(message = "secreto es obligatorio")
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
