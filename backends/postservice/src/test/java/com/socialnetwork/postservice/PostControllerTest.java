package com.socialnetwork.postservice;

import com.socialnetwork.postservice.controller.PostController;
import com.socialnetwork.postservice.model.Post;
import com.socialnetwork.postservice.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostControllerTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostController postController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPosts_ShouldReturnPostsOrderedByDateDesc() {
        Post post1 = new Post();
        post1.setId(1L);
        post1.setUsuario("usuario1");
        post1.setFechaPublicacion(LocalDateTime.now().minusDays(1));
        
        Post post2 = new Post();
        post2.setId(2L);
        post2.setUsuario("usuario2");
        post2.setFechaPublicacion(LocalDateTime.now());
        
        List<Post> expectedPosts = Arrays.asList(post2, post1);
        
        when(postRepository.findAllByOrderByFechaPublicacionDesc()).thenReturn(expectedPosts);

        List<Post> actualPosts = postController.getAllPosts();

        assertEquals(expectedPosts, actualPosts);
        assertEquals(2, actualPosts.size());
        assertEquals(2L, actualPosts.get(0).getId());
        verify(postRepository, times(1)).findAllByOrderByFechaPublicacionDesc();
    }
}
