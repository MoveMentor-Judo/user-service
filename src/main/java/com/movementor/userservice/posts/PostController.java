package com.movementor.userservice.posts;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post, HttpServletRequest request) {
        String email = (String) request.getAttribute("userEmail");
        System.out.println("Create post entered");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }
        System.out.println("Email in create post is: " + email);

        post.setAuthorId(email); // Set the author from the authenticated user
        post.setTimestamp(Instant.now());

        Post saved = postRepository.save(post);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/feed")
    public ResponseEntity<List<Post>> getFeed(HttpServletRequest request) {
        String email = (String) request.getAttribute("userEmail");
        System.out.println("🔥 /feed called by: " + email);
        //String email = (String) request.getAttribute("userEmail");
        //List<Post> posts = postRepository.findByAuthorId(email); // replace with "followed users" logic later
        //return ResponseEntity.ok(posts);
        return ResponseEntity.ok(postRepository.findAll());
    }


    @GetMapping("/author/{userId}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(postRepository.findByAuthorId(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchByThrow(@RequestParam String throwType) {
        return ResponseEntity.ok(postRepository.findByThrowTypeContainingIgnoreCase(throwType));
    }
}
