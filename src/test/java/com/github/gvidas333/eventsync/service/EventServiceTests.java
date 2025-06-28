package com.github.gvidas333.eventsync.service;

import com.github.gvidas333.eventsync.exception.ResourceNotFoundException;
import com.github.gvidas333.eventsync.model.Event;
import com.github.gvidas333.eventsync.model.Feedback;
import com.github.gvidas333.eventsync.model.Sentiment;
import com.github.gvidas333.eventsync.repository.EventRepository;
import com.github.gvidas333.eventsync.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class EventServiceTests {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private SentimentAnalysisService sentimentAnalysisService;

    @InjectMocks
    private EventService eventService;

    @Test
    void submitFeedback_should_throwResourceNotFoundException_when_eventDoesNotExist() {
        UUID nonExistentEventId = UUID.randomUUID();

        when(eventRepository.findById(nonExistentEventId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.submitFeedback(nonExistentEventId, "Feedback text");
        });
    }

    @Test
    void submitFeedback_should_saveAndReturnFeedback_when_eventExists() {
        UUID eventId = UUID.randomUUID();
        String feedbackText = "The event was amazing!";

        Event mockEvent = Event.builder().id(eventId).title("Test Event").build();
        Feedback mockSavedFeedback = Feedback.builder()
                .id(UUID.randomUUID())
                .text(feedbackText)
                .sentiment(Sentiment.POSITIVE)
                .event(mockEvent)
                .build();

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(mockEvent));
        when(sentimentAnalysisService.analyzeSentiment(feedbackText)).thenReturn(Sentiment.POSITIVE);
        when(feedbackRepository.save(any(Feedback.class))).thenReturn(mockSavedFeedback);

        Feedback result = eventService.submitFeedback(eventId, feedbackText);

        assertNotNull(result);
        assertEquals(mockSavedFeedback, result);
        assertEquals(Sentiment.POSITIVE, result.getSentiment());
        assertEquals(eventId, result.getEvent().getId());
    }
}