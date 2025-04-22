package com.movementor.userservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String email;
    private String name;

}