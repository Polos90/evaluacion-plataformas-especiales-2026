package com.evaluacion.api2.controller;

import com.evaluacion.api2.dto.LoginRequest;
import com.evaluacion.api2.entity.Usuario;
import com.evaluacion.api2.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public AuthController(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return repository.findByUsername(request.getUsername())
                .filter(user -> encoder.matches(request.getPassword(), user.getPassword()))
                .<ResponseEntity<?>>map(user ->
                        ResponseEntity.ok(Map.of("mensaje", "Login correcto", "username", user.getUsername())))
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of("mensaje", "Usuario o contraseña incorrectos")));
    }
}
