package com.example.dragon.service;

import com.example.dragon.dto.competition.RequestCompetition;
import com.example.dragon.dto.competition.ResponseCompetition;
import com.example.dragon.entity.CompetitionEntity;
import com.example.dragon.repository.CompetitionRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompetitionService {
    @Autowired
    private CompetitionRepo competitionRepo;

    public List<ResponseCompetition> getCompetitions() {
        List<CompetitionEntity> entities = competitionRepo.findAll();
        return entities.stream()
                .map(ResponseCompetition::toModel)
                .collect(Collectors.toList());
    }

    public ResponseCompetition getCompetition(Long id) {
        CompetitionEntity entity = competitionRepo.findById(id).orElse(null);
        if(entity == null) throw new EntityNotFoundException("Not found competition with id: " + id);

        return ResponseCompetition.toModel(entity);
    }

    public ResponseCompetition createCompetition(RequestCompetition competition) {
        try {
            CompetitionEntity entity = new CompetitionEntity();
            entity.setName(competition.getName());
            entity.setDateStart(competition.getDateStart());
            entity.setDateEnd(competition.getDateEnd());
            return ResponseCompetition.toModelReference(competitionRepo.save(entity));
        } catch (Exception e) {
           throw new InternalError("Something went wrong");
        }
    }

    public Long deleteCompetition(Long id) {
        try {
          competitionRepo.deleteById(id);
          return id;
        } catch (Exception e) {
            throw new InternalError("Something went wrong");
        }
    }
}