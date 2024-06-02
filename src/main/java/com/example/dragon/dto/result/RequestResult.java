package com.example.dragon.dto.result;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResult {

    @NotBlank(message = "score is mandatory")
    private String score;

    @NotBlank(message = "competitionId is mandatory")
    private Long competitionId;

    @NotBlank(message = "participantId is mandatory")
    private Long participantId;
}