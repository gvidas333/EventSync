package com.github.gvidas333.eventsync.mapper;

import com.github.gvidas333.eventsync.dto.CreateEventRequest;
import com.github.gvidas333.eventsync.model.Event;

public class EventMapper {

    public static Event toEntity(CreateEventRequest dto) {
        if (dto == null) return null;

        return Event.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
    }

}
