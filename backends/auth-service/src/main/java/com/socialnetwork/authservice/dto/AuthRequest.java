package com.socialnetwork.authservice.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos requeridos para autenticación")
public class AuthRequest {
    
    @Schema(description = "Email del usuario", example = "usuario@ejemplo.com", required = true)
    private String email;

    @Schema(description = "Contraseña del usuario", example = "miContraseñaSegura123", required = true)
    private String password;
    
}
