package com.example.dragon.model;

import com.example.dragon.entity.CompetitionEntity;
import com.example.dragon.model.result.Result;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Competition {
    private Long id;
    private String name;
    private String dateStart;
    private String dateEnd;
    private List<Result> results;

    public static Competition toModel(CompetitionEntity entity) {
        Competition model = new Competition();
        model.id = entity.getId();
        model.name = entity.getName();
        model.dateStart = entity.getDateStart();
        model.dateEnd = entity.getDateEnd();
        model.setResults(entity.getResults().stream().map(Result::toModelWithOutCompetition).collect(Collectors.toList()));
        return model;
    }

    public static Competition toModelReference(CompetitionEntity entity) {
        Competition model = new Competition();
        model.setId(entity.getId());
        model.name = entity.getName();
        model.dateStart = entity.getDateStart();
        model.dateEnd = entity.getDateEnd();
        return model;
    }

    public static CompetitionEntity toEntity(Competition model) {
        CompetitionEntity entity = new CompetitionEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDateStart(model.getDateStart());
        entity.setDateEnd(model.getDateEnd());
        return entity;
    }
}
