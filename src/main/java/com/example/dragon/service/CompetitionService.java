package com.example.dragon.service;

import com.example.dragon.entity.CompetitionEntity;
import com.example.dragon.model.Competition;
import com.example.dragon.repository.CompetitionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionService {
    @Autowired
    private CompetitionRepo competitionRepo;

    public List<Competition> getCompetitions() {
        List<CompetitionEntity> entities = competitionRepo.findAll();
        return entities.stream()
                .map(Competition::toModel)
                .collect(Collectors.toList());
    }

    public Competition getCompetition(Long id) {
        CompetitionEntity entity = competitionRepo.findById(id).orElse(null);
        return entity != null ? Competition.toModel(entity) : null;
    }

    public Competition createCompetition(Competition competition) {
        CompetitionEntity entity = new CompetitionEntity();
        entity.setName(competition.getName());
        entity.setDateStart(competition.getDateStart());
        entity.setDateEnd(competition.getDateEnd());
        return Competition.toModel(competitionRepo.save(entity));
    }

    public Long deleteCompetition(Long id) {
      competitionRepo.deleteById(id);
      return id;
    }
}