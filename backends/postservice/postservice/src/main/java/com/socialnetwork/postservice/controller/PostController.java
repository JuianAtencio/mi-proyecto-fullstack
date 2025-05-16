package com.socialnetwork.postservice.controller;

import com.socialnetwork.postservice.model.Post;
import com.socialnetwork.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository repository;

    // Obtener todas las publicaciones
    @GetMapping
    public List<Post> getAllPosts() {
        return repository.findAllByOrderByFechaPublicacionDesc();
    }

    // Crear una publicación
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
         }
        
        String nombreUsuario = authentication.getName(); // esto te da el email o username
        post.setUsuario(nombreUsuario);
        post.setFechaPublicacion(LocalDateTime.now());
        post.setTotalLikes(0);

        return ResponseEntity.ok(repository.save(post));
    }

    // Dar like a una publicación
    @PostMapping("/{id}/like")
    public ResponseEntity<?> likePost(@PathVariable Long id) {
        return repository.findById(id).map(post -> {
            post.setTotalLikes(post.getTotalLikes() + 1);
            repository.save(post);
            return ResponseEntity.ok(post);
        }).orElse(ResponseEntity.notFound().build());
    }
}
