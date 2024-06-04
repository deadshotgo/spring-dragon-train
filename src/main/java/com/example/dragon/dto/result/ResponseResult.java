package com.example.dragon.dto.result;

import com.example.dragon.dto.participant.ResponseParticipant;
import com.example.dragon.entity.ResultEntity;
import com.example.dragon.dto.competition.ResponseCompetition;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult {
    private Long id;
    private String score;
    private ResponseCompetition competition;
    private ResponseParticipant participant; // Додане поле

    public static ResponseResult toModel(ResultEntity entity) {
        ResponseResult model = new ResponseResult();
        model.setId(entity.getId());
        model.setScore(entity.getScore());
        return model;
    }

    public static ResponseResult toModelWithOutCompetition(ResultEntity entity) {
        ResponseResult model = toModel(entity);
        model.setParticipant(ResponseParticipant.toModelReference(entity.getParticipant()));
        return model;
    }

    public static ResponseResult toModelWithOutParticipant(ResultEntity entity) {
        ResponseResult model = toModel(entity);
        model.setCompetition(ResponseCompetition.toModelReference(entity.getCompetition()));
        return model;
    }

    public static ResponseResult toModelWithRelations(ResultEntity entity) {
        ResponseResult model = toModel(entity);
        model.setCompetition(ResponseCompetition.toModelReference(entity.getCompetition()));
        model.setParticipant(ResponseParticipant.toModelReference(entity.getParticipant()));
        return model;
    }

    public static ResultEntity toEntity(ResponseResult model) {
        ResultEntity entity = new ResultEntity();
        entity.setId(model.getId());
        entity.setScore(model.getScore());
        return entity;
    }
}
