package com.movementor.userservice.journal;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JournalRepository extends MongoRepository<JournalEntry, String> {
    List<JournalEntry> findByUserId(String userId);
}
