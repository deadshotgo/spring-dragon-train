package com.example.dragon.dto.result;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestResult {

    @NotBlank(message = "score is mandatory")
    private String score;

    @NotNull(message = "competitionId is mandatory")
    private Long competitionId;

    @NotNull(message = "participantId is mandatory")
    private Long participantId;
}