package com.example.dragon.model;

import com.example.dragon.entity.ParticipantEntity;
import com.example.dragon.model.result.Result;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Participant {
    private Long id;
    private String name;
    private List<Result> results;

    public static Participant toModel(ParticipantEntity entity) {
        Participant model = toModelReference(entity);
        model.setResults(entity.getResults().stream().map(Result::toModelWithOutParticipant).collect(Collectors.toList()));
        return model;
    }

    public static Participant toModelReference(ParticipantEntity entity) {
        Participant model = new Participant();
        model.id = entity.getId();
        model.name = entity.getName();
        return model;
    }

    public static ParticipantEntity toEntity(Participant model) {
        ParticipantEntity entity = new ParticipantEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        return entity;
    }
}
