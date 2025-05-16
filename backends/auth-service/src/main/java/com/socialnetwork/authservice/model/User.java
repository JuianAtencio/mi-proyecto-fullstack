package com.socialnetwork.authservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users-auth")
@Schema(description = "Entidad de usuario para autenticación")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Schema(description = "Nombres del usuario", example = "María José")
    private String nombres;

    @Schema(description = "Apellidos del usuario", example = "García López")
    private String apellidos;

    @Schema(description = "Fecha de nacimiento", example = "1995-08-20")
    private LocalDate fechaNacimiento;

    @Schema(description = "Alias/nickname del usuario", example = "mariajose95")
    private String alias;

    @Column(unique = true)
    @Schema(description = "Email del usuario (debe ser único)", example = "maria@ejemplo.com")
    private String email;

    @Schema(description = "Contraseña encriptada", example = "$2a$10$N9qo8uLOickgx2ZMRZoMy...", accessMode = Schema.AccessMode.READ_ONLY)
    private String password;
}