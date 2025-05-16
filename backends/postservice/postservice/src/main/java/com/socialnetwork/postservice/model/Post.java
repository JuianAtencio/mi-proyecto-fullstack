package com.socialnetwork.postservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private String usuario; // Se puede guardar el alias o email del usuario autenticado

    private LocalDateTime fechaPublicacion;

    private Integer totalLikes;
}
