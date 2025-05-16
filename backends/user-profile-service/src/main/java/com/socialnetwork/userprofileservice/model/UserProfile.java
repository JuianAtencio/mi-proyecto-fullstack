package com.socialnetwork.userprofileservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

     @Schema(description = "Nombres del usuario", example = "Juan Carlos")
    private String nombres;

    @Schema(description = "Apellidos del usuario", example = "Perez Gomez")
    private String apellidos;

    @Schema(description = "Fecha de nacimiento", example = "1990-05-15")
    private LocalDate fechaNacimiento;

    @Schema(description = "Alias/nickname del usuario", example = "juanperez90")
    private String alias;

    @Column(nullable = false, unique = true)
    @Schema(description = "Email del usuario (debe ser único)", example = "usuario@example.com")
    private String email;
}
