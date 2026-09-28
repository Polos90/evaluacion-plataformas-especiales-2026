package com.evaluacion.api1.client;

import com.evaluacion.api1.dto.Api2Request;
import com.evaluacion.api1.dto.OperacionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "api2", url = "${api2.url}")
public interface Api2Client {

    @PostMapping("/api/transacciones")
    OperacionResponse crear(@RequestBody Api2Request request);
}
