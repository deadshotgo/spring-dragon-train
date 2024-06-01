package com.example.dragon.model.result;

import com.example.dragon.entity.ResultEntity;
import com.example.dragon.model.Competition;
import com.example.dragon.model.Participant;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private Long id;
    private String score;
    private Competition competition;
    private Participant participant; // Додане поле

    public static Result toModel(ResultEntity entity) {
        Result model = new Result();
        model.setId(entity.getId());
        model.setScore(entity.getScore());
        return model;
    }

    public static Result toModelWithOutCompetition(ResultEntity entity) {
        Result model = toModel(entity);
        model.setParticipant(Participant.toModelReference(entity.getParticipant()));
        return model;
    }

    public static Result toModelWithOutParticipant(ResultEntity entity) {
        Result model = toModel(entity);
        model.setCompetition(Competition.toModelReference(entity.getCompetition()));
        return model;
    }

    public static Result toModelWithRelations(ResultEntity entity) {
        Result model = toModel(entity);
        model.setCompetition(Competition.toModelReference(entity.getCompetition()));
        model.setParticipant(Participant.toModelReference(entity.getParticipant()));
        return model;
    }
}
