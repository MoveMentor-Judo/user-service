package com.movementor.userservice.posts;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        System.out.println("Received POST /posts");
        post.setTimestamp(Instant.now());
        return ResponseEntity.ok(postRepository.save(post));
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
