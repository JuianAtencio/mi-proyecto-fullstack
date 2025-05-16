package com.socialnetwork.authservice.controller;

import com.socialnetwork.authservice.dto.AuthRequest;
import com.socialnetwork.authservice.dto.AuthResponse;
import com.socialnetwork.authservice.model.User;
import com.socialnetwork.authservice.repository.UserRepository;
import com.socialnetwork.authservice.config.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API for user authentication and JWT management")
@SecurityRequirement(name = "bearerAuth") // Requiere autenticación para todos los endpoints
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Operation(
        summary = "User login",
        description = "Authenticates user credentials and returns JWT token",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User credentials",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthRequest.class),
                examples = {
                    @ExampleObject(
                        name = "Valid credentials",
                        value = "{\"email\": \"user@example.com\", \"password\": \"password123\"}",
                        description = "Example of valid credentials"),
                    @ExampleObject(
                        name = "Invalid credentials",
                        value = "{\"email\": \"user@example.com\", \"password\": \"wrongpassword\"}",
                        description = "Example of invalid credentials")
                }
            )
        )
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Authentication successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthResponse.class),
                examples = @ExampleObject(
                    value = "{\"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid credentials",
            content = @Content(
                mediaType = "text/plain",
                examples = @ExampleObject(value = "\"Invalid password\"")
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content(
                mediaType = "text/plain",
                examples = @ExampleObject(value = "\"User not found\"")
            )
        )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(
        summary = "Validate token",
        description = "Validates the JWT token and confirms authentication",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Token is valid",
            content = @Content(
                mediaType = "text/plain",
                examples = @ExampleObject(value = "\"Token is valid. Access granted!\"")
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Invalid token",
            content = @Content(
                mediaType = "text/plain",
                examples = @ExampleObject(value = "\"Unauthorized\"")
            )
        )
    })
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken() {
        return ResponseEntity.ok("Token is valid. Access granted!");
    }

    @Operation(
        summary = "Check service health",
        description = "Public endpoint to check if authentication service is running"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Service is running",
        content = @Content(
            mediaType = "text/plain",
            examples = @ExampleObject(value = "\"Authentication service is up and running\"")
        )
    )
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Authentication service is up and running");
    }
}