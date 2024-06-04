package com.example.dragon.service;

import com.example.dragon.dto.competition.ResponseCompetition;
import com.example.dragon.dto.participant.ResponseParticipant;
import com.example.dragon.dto.result.RequestResult;
import com.example.dragon.dto.result.ResponseResult;
import com.example.dragon.entity.ResultEntity;
import com.example.dragon.repository.ResultRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultService {
    @Autowired
    private final ResultRepo resultRepo;
    private final CompetitionService competitionService;
    private final ParticipantService participantService;

    public List<ResponseResult> getResults() {
        return resultRepo.findAll().stream()
                .map(ResponseResult::toModelWithRelations)
                .collect(Collectors.toList());
    }

    public ResponseResult getResult(Long id) {
        ResultEntity entity = resultRepo.findById(id).orElse(null);
        if (entity == null) throw new EntityNotFoundException("Result with id " + id + " not found");
        return ResponseResult.toModelWithRelations(entity);
    }

    public ResponseResult createResult(RequestResult resultRequest) {
        try {
            ResponseCompetition responseCompetition = competitionService.getCompetition(resultRequest.getCompetitionId());
            ResponseParticipant responseParticipant = participantService.getParticipant(resultRequest.getParticipantId());

            ResultEntity entity = new ResultEntity();
            entity.setScore(resultRequest.getScore());
            entity.setCompetition(ResponseCompetition.toEntity(responseCompetition));
            entity.setParticipant(ResponseParticipant.toEntity(responseParticipant));

            return ResponseResult.toModelWithRelations(resultRepo.save(entity));
        } catch (Exception e) {
          throw new InternalError("Something went wrong");
        }
    }

    public Long deleteResult(Long id) {
        try {
            resultRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new InternalError("Something went wrong");
        }
    }
}
