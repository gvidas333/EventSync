package com.github.gvidas333.eventsync.service;

import com.github.gvidas333.eventsync.model.Event;
import com.github.gvidas333.eventsync.model.Feedback;
import com.github.gvidas333.eventsync.model.Sentiment;
import com.github.gvidas333.eventsync.repository.EventRepository;
import com.github.gvidas333.eventsync.repository.FeedbackRepository;
import exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final FeedbackRepository feedbackRepository;
    private final SentimentAnalysisService sentimentAnalysisService;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Feedback submitFeedback(UUID eventId, String feedbackText) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + eventId));

        Sentiment sentiment = sentimentAnalysisService.analyzeSentiment(feedbackText);

        Feedback newFeedback = Feedback.builder()
                .text(feedbackText)
                .sentiment(sentiment)
                .timestamp(LocalDateTime.now())
                .event(event)
                .build();

        return feedbackRepository.save(newFeedback);
    }
}
