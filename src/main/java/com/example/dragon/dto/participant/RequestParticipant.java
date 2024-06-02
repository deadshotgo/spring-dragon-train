package com.example.dragon.dto.participant;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestParticipant {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
