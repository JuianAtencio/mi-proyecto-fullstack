package com.socialnetwork.authservice.config;

import com.socialnetwork.authservice.model.User;
import com.socialnetwork.authservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .nombres("Julian")
                    .apellidos("Atencio")
                    .fechaNacimiento(LocalDate.of(1990, 5, 20))
                    .alias("juliancho")
                    .email("julian@gmail.com")
                    .password(new BCryptPasswordEncoder().encode("12345"))
                    .build());
        }
    }
}
