package com.example.dragon.dto.result;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestResult {

    @NotBlank(message = "score is mandatory")
    private String score;

    @NotNull(message = "competitionId is mandatory")
    private Long competitionId;

    @NotNull(message = "participantId is mandatory")
    private Long participantId;
}