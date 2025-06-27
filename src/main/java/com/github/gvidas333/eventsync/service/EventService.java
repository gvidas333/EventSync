package com.github.gvidas333.eventsync.service;

import com.github.gvidas333.eventsync.model.Event;
import com.github.gvidas333.eventsync.repository.EventRepository;
import com.github.gvidas333.eventsync.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final FeedbackRepository feedbackRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }
}
