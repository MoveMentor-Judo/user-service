package com.movementor.userservice.posts;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String authorId;
    private String content;
    private String videoUrl;
    private String throwType;
    private Instant timestamp;
    private List<String> kudosUserIds;
}
