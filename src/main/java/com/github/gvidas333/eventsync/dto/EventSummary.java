package com.github.gvidas333.eventsync.dto;

import com.github.gvidas333.eventsync.model.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventSummary {
    private long totalFeedback;
    private Map<String, Long> sentimentCounts;
    private Map<String, Double> sentimentPercentages;

    public static EventSummary from(List<Feedback> feedbackList) {
        long totalFeedback = feedbackList.size();

        if (totalFeedback == 0) {
            return EventSummary.builder()
                    .totalFeedback(0)
                    .sentimentCounts(Collections.emptyMap())
                    .sentimentPercentages(Collections.emptyMap())
                    .build();
        }

        Map<String, Long> sentimentCounts = feedbackList.stream()
                .collect(Collectors.groupingBy(
                        feedback -> feedback.getSentiment().name(),
                        Collectors.counting()
                ));

        Map<String, Double> sentimentPercentages = sentimentCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (double) entry.getValue() / totalFeedback
                ));

        return EventSummary.builder()
                .totalFeedback(totalFeedback)
                .sentimentCounts(sentimentCounts)
                .sentimentPercentages(sentimentPercentages)
                .build();
    }
}
