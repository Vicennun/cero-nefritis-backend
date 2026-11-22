package com.example.cero_nefritis_backend.controller;

import com.example.cero_nefritis_backend.model.Rol;
import com.example.cero_nefritis_backend.model.Usuario;
import com.example.cero_nefritis_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepo;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        if (usuarioRepo.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El correo electrónico ya se encuentra registrado."));
        }
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.PACIENTE);
        }
        // Evita error de restricción NOT NULL si el registro rápido aún no solicita RUT
        if (usuario.getRut() == null || usuario.getRut().isBlank()) {
            usuario.setRut("PENDIENTE-" + System.currentTimeMillis());
        }
        usuario.setConsentimientoInformado(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepo.save(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        return usuarioRepo.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Correo o contraseña incorrectos.")));
    }
}