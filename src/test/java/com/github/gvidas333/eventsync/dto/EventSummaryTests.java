package com.github.gvidas333.eventsync.dto;

import com.github.gvidas333.eventsync.model.Feedback;
import com.github.gvidas333.eventsync.model.Sentiment;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class EventSummaryTests {

    @Test
    void should_calculateSummaryCorrectly_when_givenListOfFeedback() {
        Feedback feedback1 = Feedback.builder().sentiment(Sentiment.POSITIVE).build();
        Feedback feedback2 = Feedback.builder().sentiment(Sentiment.POSITIVE).build();
        Feedback feedback3 = Feedback.builder().sentiment(Sentiment.NEGATIVE).build();
        Feedback feedback4 = Feedback.builder().sentiment(Sentiment.NEGATIVE).build();
        List<Feedback> mockFeedbackList = List.of(feedback1, feedback2, feedback3, feedback4);

        EventSummary summary = EventSummary.from(mockFeedbackList);

        assertEquals(4, summary.getTotalFeedback());
        assertEquals(2, summary.getSentimentCounts().get("POSITIVE"));
        assertEquals(2, summary.getSentimentCounts().get("NEGATIVE"));
        assertNull(summary.getSentimentCounts().get("NEUTRAL"));

        assertEquals(2.0/4.0, summary.getSentimentPercentages().get("POSITIVE"));
        assertEquals(2.0/4.0, summary.getSentimentPercentages().get("NEGATIVE"));
    }

    @Test
    void should_returnEmptySummary_when_givenEmptyList() {
        List<Feedback> emptyList = List.of();

        EventSummary summary = EventSummary.from(emptyList);

        assertEquals(0, summary.getTotalFeedback());
        assertTrue(summary.getSentimentCounts().isEmpty());
        assertTrue(summary.getSentimentPercentages().isEmpty());
    }
}