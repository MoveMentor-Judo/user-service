package com.movementor.userservice.posts;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findByAuthorId(String authorId);
    List<Post> findByThrowTypeContainingIgnoreCase(String throwType);
}
