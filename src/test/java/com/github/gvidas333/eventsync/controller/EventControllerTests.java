package com.github.gvidas333.eventsync.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gvidas333.eventsync.dto.CreateEventRequest;
import com.github.gvidas333.eventsync.dto.CreateFeedbackRequest;
import com.github.gvidas333.eventsync.model.Event;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EventControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_createEventSuccessfully_when_validRequest() throws Exception {
        CreateEventRequest eventRequest = new CreateEventRequest();
        eventRequest.setTitle("title");
        eventRequest.setDescription("description");

        mockMvc.perform(post("/events")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(eventRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("title"));

    }

    @Test
    void should_return400BadRequest_when_creatingEventWithEmptyTitle() throws Exception {
        CreateEventRequest badRequest = new CreateEventRequest();
        badRequest.setTitle("");

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void submitFeedback_should_return404NotFound_when_eventDoesNotExist() throws Exception {
        UUID nonExistentEventId = UUID.randomUUID();
        CreateFeedbackRequest feedbackRequest = new CreateFeedbackRequest();
        feedbackRequest.setText("Some feedback");

        mockMvc.perform(post("/events/" + nonExistentEventId + "/feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return404NotFound_when_requestingSummaryForNonExistingEvent() throws Exception {
        UUID nonExistingEventId = UUID.randomUUID();
        mockMvc.perform(get("/events/" + nonExistingEventId + "/summary"))
                .andExpect(status().isNotFound());
    }
}