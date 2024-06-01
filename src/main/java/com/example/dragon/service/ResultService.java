package com.example.dragon.service;

import com.example.dragon.entity.ResultEntity;
import com.example.dragon.model.Competition;
import com.example.dragon.model.Participant;
import com.example.dragon.model.result.Result;
import com.example.dragon.model.result.ResultRequest;
import com.example.dragon.repository.ResultRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {
    @Autowired
    private ResultRepo resultRepo;
    private final CompetitionService competitionService;
    private final ParticipantService participantService;

    public List<Result> getResults() {
        return resultRepo.findAll().stream()
                .map(Result::toModelWithRelations)
                .collect(Collectors.toList());
    }

    public Result getResult(Long id) {
        ResultEntity entity = resultRepo.findById(id).orElse(null);
        return entity != null ? Result.toModelWithRelations(entity) : null;
    }

    public Result createResult(ResultRequest resultRequest) {
        Competition competition = competitionService.getCompetition(resultRequest.getCompetitionId());
        Participant participant = participantService.getParticipant(resultRequest.getParticipantId());

        ResultEntity entity = new ResultEntity();
        entity.setScore(resultRequest.getScore());
        entity.setCompetition(Competition.toEntity(competition)); // Перетворення Competition в CompetitionEntity
        entity.setParticipant(Participant.toEntity(participant)); // Перетворення Participant в ParticipantEntity

        return Result.toModelWithRelations(resultRepo.save(entity));
    }

    public Long deleteResult(Long id) {
        resultRepo.deleteById(id);
        return id;
    }
}
