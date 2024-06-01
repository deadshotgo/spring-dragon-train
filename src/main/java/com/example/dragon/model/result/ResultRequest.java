package com.example.dragon.model.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultRequest {
    private String score;
    private Long competitionId;
    private Long participantId;
}