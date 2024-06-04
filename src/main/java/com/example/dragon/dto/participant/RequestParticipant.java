package com.example.dragon.dto.participant;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestParticipant {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
