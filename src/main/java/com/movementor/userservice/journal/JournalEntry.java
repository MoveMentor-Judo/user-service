package com.movementor.userservice.journal;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "journals")
public class JournalEntry {
    @Id
    private String id;
    private String userId;
    private LocalDate date;
    private String whatIDid;
    private String whatILearned;
    private String notes;
}
