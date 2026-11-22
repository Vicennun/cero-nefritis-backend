package com.example.cero_nefritis_backend.controller;

import com.example.cero_nefritis_backend.model.Rol;
import com.example.cero_nefritis_backend.model.Usuario;
import com.example.cero_nefritis_backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return usuarioRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pacientes")
    public List<Usuario> listarPacientes() {
        return usuarioRepo.findAll().stream()
                .filter(u -> u.getRol() == Rol.PACIENTE)
                .toList();
    }
}