package com.github.gvidas333.eventsync.service;

import com.github.gvidas333.eventsync.dto.CreateEventRequest;
import com.github.gvidas333.eventsync.dto.EventSummary;
import com.github.gvidas333.eventsync.mapper.EventMapper;
import com.github.gvidas333.eventsync.model.Event;
import com.github.gvidas333.eventsync.model.Feedback;
import com.github.gvidas333.eventsync.model.Sentiment;
import com.github.gvidas333.eventsync.repository.EventRepository;
import com.github.gvidas333.eventsync.repository.FeedbackRepository;
import com.github.gvidas333.eventsync.exception.ResourceNotFoundException;
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

    public Event createEvent(CreateEventRequest createEventRequestDto) {
        Event newEvent = EventMapper.toEntity(createEventRequestDto);

        return eventRepository.save(newEvent);
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

    public EventSummary getEventSummary(UUID eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new ResourceNotFoundException("Event not found with id: " + eventId);
        }

        List<Feedback> feedbackList = feedbackRepository.findByEventId(eventId);

        return EventSummary.from(feedbackList);
    }
}
