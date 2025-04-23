package com.movementor.userservice.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {
    @Autowired
    private JournalRepository journalRepository;

    @PostMapping
    public ResponseEntity<JournalEntry> create(@RequestBody JournalEntry entry) {
        return ResponseEntity.ok(journalRepository.save(entry));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JournalEntry>> list(@PathVariable String userId) {
        return ResponseEntity.ok(journalRepository.findByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> update(@PathVariable String id, @RequestBody JournalEntry entry) {
        entry.setId(id);
        return ResponseEntity.ok(journalRepository.save(entry));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        journalRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

