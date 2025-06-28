package com.github.gvidas333.eventsync.controller;

import com.github.gvidas333.eventsync.dto.CreateEventRequest;
import com.github.gvidas333.eventsync.dto.CreateFeedbackRequest;
import com.github.gvidas333.eventsync.dto.EventSummary;
import com.github.gvidas333.eventsync.model.Event;
import com.github.gvidas333.eventsync.model.Feedback;
import com.github.gvidas333.eventsync.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Tag(name = "Events", description = "API endpoints for managing events and feedback")
public class EventController {

    private final EventService eventService;

    @Operation(summary = "Create a new event")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody CreateEventRequest createEventRequest) {
        return eventService.createEvent(createEventRequest);
    }

    @Operation(summary = "Get all events")
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @Operation(summary = "Submit feedback for an event")
    @PostMapping("/{eventId}/feedback")
    public Feedback submitFeedback(@PathVariable UUID eventId, @RequestBody CreateFeedbackRequest createFeedbackRequest) {
        return eventService.submitFeedback(eventId, createFeedbackRequest.getText());
    }

    @Operation(summary = "Get event summary")
    @GetMapping("/{eventId}/summary")
    public EventSummary getEventSummary(@PathVariable UUID eventId) {
        return eventService.getEventSummary(eventId);
    }
}
