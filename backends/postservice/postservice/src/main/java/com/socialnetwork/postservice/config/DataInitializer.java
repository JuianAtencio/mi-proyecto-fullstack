package com.socialnetwork.postservice.config;

import com.socialnetwork.postservice.model.Post;
import com.socialnetwork.postservice.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final PostRepository repository;

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            repository.save(Post.builder()
                    .mensaje("Mensaje de bienvenida desde PostService")
                    .usuario("lauras")
                    .fechaPublicacion(LocalDateTime.now().minusDays(1))
                    .totalLikes(3)
                    .build());

            repository.save(Post.builder()
                    .mensaje("Mensaje Adicional desde PostService")
                    .usuario("juanp")
                    .fechaPublicacion(LocalDateTime.now().minusHours(5))
                    .totalLikes(5)
                    .build());
        }
    }
}
