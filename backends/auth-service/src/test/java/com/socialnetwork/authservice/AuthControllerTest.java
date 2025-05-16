package com.socialnetwork.authservice;

import com.socialnetwork.authservice.config.JwtUtil;
import com.socialnetwork.authservice.controller.AuthController;
import com.socialnetwork.authservice.dto.AuthRequest;
import com.socialnetwork.authservice.dto.AuthResponse;
import com.socialnetwork.authservice.model.User;
import com.socialnetwork.authservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    private AuthController authController;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtUtil = new JwtUtil();
        authController = new AuthController(userRepository, passwordEncoder, jwtUtil);
    }

    /*Prueba que valida la contraseña sea correcta */
    @Test
    public void login_shouldReturnToken_whenCredentialsAreValid() {
        // Arrange
        AuthRequest request = new AuthRequest("julian@example.com", "password123");
        User user = new User();
        user.setEmail("julian@example.com");
        user.setPassword("$2a$10$dummyHash");

        when(userRepository.findByEmail("julian@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "$2a$10$dummyHash")).thenReturn(true);

        // Act
        ResponseEntity<?> response = authController.login(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        AuthResponse body = (AuthResponse) response.getBody();
        assertNotNull(body);
        assertNotNull(body.getToken());
    }

    /*Prueba que valida si el password no sea valido */
    @Test
    public void login_shouldReturnBadRequest_whenPasswordIsInvalid() {
        AuthRequest request = new AuthRequest("julian@example.com", "wrongpass");
        User user = new User();
        user.setEmail("julian@example.com");
        user.setPassword("$2a$10$dummyHash");

        when(userRepository.findByEmail("julian@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpass", "$2a$10$dummyHash")).thenReturn(false);

        ResponseEntity<?> response = authController.login(request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid password", response.getBody());
    }

    /*Prueba que valida que el token se ha generado correctamente */
    @Test
    public void validateToken_shouldReturnSuccessMessage() {
        // Act
        ResponseEntity<String> response = authController.validateToken();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Token is valid. Access granted!", response.getBody());
    }

    /*Prueba de validación que conteste correctamente la autenticación */
    @Test
    public void healthCheck_shouldReturnServiceStatus() {
        // Act
        ResponseEntity<String> response = authController.healthCheck();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Authentication service is up and running", response.getBody());
    }

    /*Prueba de validación que el usuario no exista */
    @Test
    public void login_shouldThrowException_whenUserNotFound() {
        AuthRequest request = new AuthRequest("noexist@example.com", "password");

        when(userRepository.findByEmail("noexist@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authController.login(request);
        });

        assertEquals("User not found", exception.getMessage());
    }
}