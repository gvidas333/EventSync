package com.github.gvidas333.eventsync.controller;

import com.github.gvidas333.eventsync.dto.CreateFeedbackRequest;
import com.github.gvidas333.eventsync.dto.EventSummary;
import com.github.gvidas333.eventsync.model.Event;
import com.github.gvidas333.eventsync.model.Feedback;
import com.github.gvidas333.eventsync.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping("/{eventId}/feedback")
    public Feedback submitFeedback(@PathVariable UUID eventId, @RequestBody CreateFeedbackRequest createFeedbackRequest) {
        return eventService.submitFeedback(eventId, createFeedbackRequest.getText());
    }

    @GetMapping("/{eventId}/summary")
    public EventSummary getEventSummary(@PathVariable UUID eventId) {
        return eventService.getEventSummary(eventId);
    }
}
