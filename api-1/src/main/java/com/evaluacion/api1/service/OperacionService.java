package com.evaluacion.api1.service;

import com.evaluacion.api1.client.Api2Client;
import com.evaluacion.api1.dto.*;
import org.springframework.stereotype.Service;

@Service
public class OperacionService {

    private final Api2Client api2Client;
    private final AesService aesService;

    public OperacionService(Api2Client api2Client, AesService aesService) {
        this.api2Client = api2Client;
        this.aesService = aesService;
    }

    public OperacionResponse procesar(OperacionRequest request) {
        String secretoDescifrado = aesService.decrypt(request.getSecreto());

        Api2Request requestApi2 = new Api2Request(
                request.getOperacion(),
                request.getImporte(),
                request.getCliente(),
                secretoDescifrado
        );

        return api2Client.crear(requestApi2);
    }
}
