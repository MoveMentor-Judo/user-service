package com.movementor.userservice.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        comment.setTimestamp(Instant.now());
        return ResponseEntity.ok(commentRepository.save(comment));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsForPost(@PathVariable String postId) {
        return ResponseEntity.ok(commentRepository.findByPostId(postId));
    }
}

