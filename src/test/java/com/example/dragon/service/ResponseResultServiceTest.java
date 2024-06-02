package com.example.dragon.service;

import com.example.dragon.dto.competition.ResponseCompetition;
import com.example.dragon.dto.participant.ResponseParticipant;
import com.example.dragon.entity.CompetitionEntity;
import com.example.dragon.entity.ParticipantEntity;
import com.example.dragon.entity.ResultEntity;
import com.example.dragon.dto.result.ResponseResult;
import com.example.dragon.dto.result.RequestResult;
import com.example.dragon.repository.ResultRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class ResponseResultServiceTest {

    @Mock
    private ResultRepo resultRepo;

    @Mock
    private CompetitionService competitionService;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private ResultService resultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testCreateResult() {
        RequestResult resultRequest = new RequestResult();
        resultRequest.setScore("10");
        resultRequest.setCompetitionId(1L);
        resultRequest.setParticipantId(1L);

        ResponseCompetition responseCompetition = new ResponseCompetition();
        responseCompetition.setId(1L);
        responseCompetition.setName("Футбольний матч");

        ResponseParticipant responseParticipant = new ResponseParticipant();
        responseParticipant.setId(1L);
        responseParticipant.setName("Oleg");

        when(competitionService.getCompetition(1L)).thenReturn(responseCompetition);
        when(participantService.getParticipant(1L)).thenReturn(responseParticipant);

        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setId(1L);
        resultEntity.setScore("10");
        resultEntity.setCompetition(ResponseCompetition.toEntity(responseCompetition));
        resultEntity.setParticipant(ResponseParticipant.toEntity(responseParticipant));

        when(resultRepo.save(any(ResultEntity.class))).thenReturn(resultEntity);

        ResponseResult result = resultService.createResult(resultRequest);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("10", result.getScore());
        verify(resultRepo, times(1)).save(any(ResultEntity.class));
    }

    @Test
    void testGetResults() {

        CompetitionEntity competitionEntity = new CompetitionEntity();
        competitionEntity.setId(1L);
        competitionEntity.setName("Competition 1");

        ParticipantEntity participantEntity = new ParticipantEntity();
        participantEntity.setId(1L);
        participantEntity.setName("Participant 1");


        ResultEntity resultEntity1 = new ResultEntity();
        resultEntity1.setId(1L);
        resultEntity1.setScore("12");
        resultEntity1.setCompetition(competitionEntity);
        resultEntity1.setParticipant(participantEntity);

        ResultEntity resultEntity2 = new ResultEntity();
        resultEntity2.setId(2L);
        resultEntity2.setScore("50");
        resultEntity2.setCompetition(competitionEntity);
        resultEntity2.setParticipant(participantEntity);

        when(resultRepo.findAll()).thenReturn(Arrays.asList(resultEntity1, resultEntity2));

        List<ResponseResult> results = resultService.getResults();

        assertEquals(2, results.size());
        verify(resultRepo, times(1)).findAll();
    }

    @Test
    void testGetResult() {
        CompetitionEntity competitionEntity = new CompetitionEntity();
        competitionEntity.setId(1L);
        competitionEntity.setName("Competition 1");

        ParticipantEntity participantEntity = new ParticipantEntity();
        participantEntity.setId(1L);
        participantEntity.setName("Participant 1");

        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setId(1L);
        resultEntity.setScore("12");
        resultEntity.setCompetition(competitionEntity);
        resultEntity.setParticipant(participantEntity);

        when(resultRepo.findById(1L)).thenReturn(Optional.of(resultEntity));

        ResponseResult result = resultService.getResult(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("12", result.getScore());
        verify(resultRepo, times(1)).findById(1L);
    }


}
