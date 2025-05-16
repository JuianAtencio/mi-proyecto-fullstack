package com.socialnetwork.userprofileservice.config;

import com.socialnetwork.userprofileservice.model.UserProfile;
import com.socialnetwork.userprofileservice.repository.UserProfileRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserProfileRepository repository;

    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            repository.save(UserProfile.builder()
                    .nombres("Laura")
                    .apellidos("Sánchez")
                    .fechaNacimiento(LocalDate.of(1992, 3, 14))
                    .alias("lauras")
                    .email("laura@example.com")
                    .build());
        }
    }
}