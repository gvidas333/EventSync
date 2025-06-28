package com.github.gvidas333.eventsync.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFeedbackRequest {

    @NotBlank
    private String text;
}
