package com.example.dragon.unit;

import com.example.dragon.dto.competition.ResponseCompetition;
import com.example.dragon.dto.participant.ResponseParticipant;
import com.example.dragon.dto.result.RequestResult;
import com.example.dragon.dto.result.ResponseResult;
import com.example.dragon.entity.CompetitionEntity;
import com.example.dragon.entity.ParticipantEntity;
import com.example.dragon.entity.ResultEntity;
import com.example.dragon.repository.ResultRepo;
import com.example.dragon.service.CompetitionService;
import com.example.dragon.service.ParticipantService;
import com.example.dragon.service.ResultService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResultServiceTest {

    @Mock
    private ResultRepo resultRepo;

    @Mock
    private CompetitionService competitionService;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private ResultService resultService;

    @Test
    public void testGetResults() {
        ResultEntity resultEntity1 = createdEntityResult(1L, "50");
        ResultEntity resultEntity2 = createdEntityResult(1L, "70");

        when(resultRepo.findAll()).thenReturn(Arrays.asList(resultEntity1, resultEntity2));

        List<ResponseResult> results = resultService.getResults();

        assertEquals(2, results.size());
    }

    @Test
    public void testGetResult() {
        Long id = 1L;
        ResultEntity resultEntity1 = createdEntityResult(id, "50");
        when(resultRepo.findById(id)).thenReturn(Optional.of(resultEntity1));

        ResponseResult responseResult = resultService.getResult(id);

        assertEquals(id, responseResult.getId());
    }

    @Test
    public void testCreateResult() {
        RequestResult requestResult = new RequestResult();
        requestResult.setCompetitionId(1L);
        requestResult.setParticipantId(1L);
        requestResult.setScore("10");

        ResponseCompetition responseCompetition = new ResponseCompetition(1L, "Competition 1", LocalDate.now().toString(), LocalDate.now().toString(), Collections.emptyList());
        when(competitionService.getCompetition(requestResult.getCompetitionId())).thenReturn(responseCompetition);

        ResponseParticipant responseParticipant =  new ResponseParticipant(1L, "Participant 1", Collections.emptyList());
        when(participantService.getParticipant(requestResult.getParticipantId())).thenReturn(responseParticipant);

        ResultEntity resultEntity1 = createdEntityResult(1L, "50");

        when(resultRepo.save(any(ResultEntity.class))).thenReturn(resultEntity1);
        ResponseResult createdResult = resultService.createResult(requestResult);

        assertEquals(1L, createdResult.getId());
    }

    @Test
    public void testDeleteResult() {
        Long id = 1L;
        doNothing().when(resultRepo).deleteById(id);

        Long deletedId = resultService.deleteResult(id);

        assertEquals(id, deletedId);
    }

    private ResultEntity createdEntityResult(Long id, String score) {
        ResultEntity entity = new ResultEntity();
        entity.setId(id);
        entity.setScore(score);
        entity.setParticipant(new ParticipantEntity(1L, "Participant 1", Collections.emptyList()));
        entity.setCompetition(new CompetitionEntity(1L, "Competition 1", LocalDate.now().toString(), LocalDate.now().toString(), Collections.emptyList()));
        return entity;
    }
}
