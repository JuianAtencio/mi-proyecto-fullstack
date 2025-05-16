package com.socialnetwork.authservice.controller;

import com.socialnetwork.authservice.dto.AuthRequest;
import com.socialnetwork.authservice.dto.AuthResponse;
import com.socialnetwork.authservice.model.User;
import com.socialnetwork.authservice.repository.UserRepository;
import com.socialnetwork.authservice.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        System.out.println("TOKEN: " + token);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/check")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("Token válido. ¡Acceso autorizado!");
    }
}
