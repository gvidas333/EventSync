package com.github.gvidas333.eventsync.repository;

import com.github.gvidas333.eventsync.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {

    List<Feedback> findByEventId(UUID eventId);
}
