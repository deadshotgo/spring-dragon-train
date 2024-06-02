package com.example.dragon.dto.competition;

import com.example.dragon.dto.result.ResponseResult;
import com.example.dragon.entity.CompetitionEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCompetition {
    private Long id;
    private String name;
    private String dateStart;
    private String dateEnd;
    private List<ResponseResult> results;

    public static ResponseCompetition toModel(CompetitionEntity entity) {
        ResponseCompetition model = new ResponseCompetition();
        model.id = entity.getId();
        model.name = entity.getName();
        model.dateStart = entity.getDateStart();
        model.dateEnd = entity.getDateEnd();
        model.setResults(entity.getResults().stream().map(ResponseResult::toModelWithOutCompetition).collect(Collectors.toList()));
        return model;
    }

    public static ResponseCompetition toModelReference(CompetitionEntity entity) {
        ResponseCompetition model = new ResponseCompetition();
        model.setId(entity.getId());
        model.name = entity.getName();
        model.dateStart = entity.getDateStart();
        model.dateEnd = entity.getDateEnd();
        return model;
    }

    public static CompetitionEntity toEntity(ResponseCompetition model) {
        CompetitionEntity entity = new CompetitionEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDateStart(model.getDateStart());
        entity.setDateEnd(model.getDateEnd());
        return entity;
    }
}
