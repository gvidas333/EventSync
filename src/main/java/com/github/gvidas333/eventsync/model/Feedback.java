package com.github.gvidas333.eventsync.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String text;

    @Enumerated(EnumType.STRING)
    private Sentiment sentiment;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}
