package com.example.dragon.dto.participant;

import com.example.dragon.entity.ParticipantEntity;
import com.example.dragon.dto.result.ResponseResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseParticipant {
    private Long id;
    private String name;
    private List<ResponseResult> results;

    public static ResponseParticipant toModel(ParticipantEntity entity) {
        ResponseParticipant model = toModelReference(entity);
        model.setResults(entity.getResults().stream().map(ResponseResult::toModelWithOutParticipant).collect(Collectors.toList()));
        return model;
    }

    public static ResponseParticipant toModelReference(ParticipantEntity entity) {
        ResponseParticipant model = new ResponseParticipant();
        model.id = entity.getId();
        model.name = entity.getName();
        return model;
    }

    public static ParticipantEntity toEntity(ResponseParticipant model) {
        ParticipantEntity entity = new ParticipantEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        return entity;
    }
}
