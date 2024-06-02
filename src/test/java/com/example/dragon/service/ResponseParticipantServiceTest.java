package com.example.dragon.service;

import com.example.dragon.dto.participant.ResponseParticipant;
import com.example.dragon.entity.CompetitionEntity;
import com.example.dragon.entity.ParticipantEntity;
import com.example.dragon.entity.ResultEntity;
import com.example.dragon.dto.participant.RequestParticipant;
import com.example.dragon.repository.ParticipantRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ResponseParticipantServiceTest {

    @Mock
    private ParticipantRepo participantRepo;

    @InjectMocks
    private ParticipantService participantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createParticipant() {
       RequestParticipant requestParticipant = new RequestParticipant();
        requestParticipant.setName("Oleg");

        ParticipantEntity participantEntity = new ParticipantEntity();
        participantEntity.setId(1L);
        participantEntity.setName(requestParticipant.getName());

        when(participantRepo.save(any(ParticipantEntity.class))).thenReturn(participantEntity);
        ResponseParticipant responseParticipant = participantService.createParticipant(requestParticipant);

        assertNotNull(responseParticipant);
        assertEquals(1L, responseParticipant.getId());
        assertEquals(requestParticipant.getName(), responseParticipant.getName());
        verify(participantRepo, times(1)).save(any(ParticipantEntity.class));
    }

    @Test
    void getParticipant() {
        ParticipantEntity participant = new ParticipantEntity();
        participant.setId(1L);
        participant.setName("participantEntity 1");

        CompetitionEntity competitionEntity = new CompetitionEntity();
        competitionEntity.setId(1L);
        competitionEntity.setName("Competition 1");

        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setId(1L);
        resultEntity.setScore("12");
        resultEntity.setParticipant(participant);
        resultEntity.setCompetition(competitionEntity);


        ParticipantEntity participantEntity = new ParticipantEntity();
        participantEntity.setId(participant.getId());
        participantEntity.setName(participant.getName());
        participantEntity.setResults(new ArrayList<>());
        participantEntity.getResults().add(resultEntity);

        when(participantRepo.findById(1L)).thenReturn(Optional.of(participantEntity));

        ResponseParticipant response = participantService.getParticipant(1L);

        assertNotNull(participant);
        assertEquals(participant.getId(), response.getId());
        assertEquals(participant.getName(), response.getName());
        verify(participantRepo, times(1)).findById(1L);
    }

    @Test
    void getParticipants() {
        // Створення першого учасника та його результатів
        ParticipantEntity participantEntity1 = new ParticipantEntity();
        participantEntity1.setId(1L);
        participantEntity1.setName("Participant 1");

        CompetitionEntity competitionEntity1 = new CompetitionEntity();
        competitionEntity1.setId(1L);
        competitionEntity1.setName("Competition 1");

        ResultEntity resultEntity1 = new ResultEntity();
        resultEntity1.setId(1L);
        resultEntity1.setScore("12");
        resultEntity1.setParticipant(participantEntity1);
        resultEntity1.setCompetition(competitionEntity1);

        participantEntity1.setResults(new ArrayList<>());
        participantEntity1.getResults().add(resultEntity1);

        // Створення другого учасника та його результатів
        ParticipantEntity participantEntity2 = new ParticipantEntity();
        participantEntity2.setId(2L);
        participantEntity2.setName("Participant 2");

        CompetitionEntity competitionEntity2 = new CompetitionEntity();
        competitionEntity2.setId(2L);
        competitionEntity2.setName("Competition 2");

        ResultEntity resultEntity2 = new ResultEntity();
        resultEntity2.setId(2L);
        resultEntity2.setScore("15");
        resultEntity2.setParticipant(participantEntity2);
        resultEntity2.setCompetition(competitionEntity2);

        participantEntity2.setResults(new ArrayList<>());
        participantEntity2.getResults().add(resultEntity2);

        // Налаштування мока репозиторію для повернення списку учасників
        when(participantRepo.findAll()).thenReturn(Arrays.asList(participantEntity1, participantEntity2));

        // Виклик методу сервісу
        List<ResponseParticipant> responseParticipants = participantService.getParticipants();

        // Перевірка результатів
        assertNotNull(responseParticipants, "Список учасників не повинен бути null");
        assertEquals(2, responseParticipants.size(), "Розмір списку учасників має бути 2");

        ResponseParticipant responseParticipant1 = responseParticipants.get(0);
        assertEquals(1L, responseParticipant1.getId(), "ID першого учасника має бути 1");
        assertEquals("Participant 1", responseParticipant1.getName(), "Ім'я першого учасника має бути 'Participant 1'");

        ResponseParticipant responseParticipant2 = responseParticipants.get(1);
        assertEquals(2L, responseParticipant2.getId(), "ID другого учасника має бути 2");
        assertEquals("Participant 2", responseParticipant2.getName(), "Ім'я другого учасника має бути 'Participant 2'");

        // Перевірка виклику методу репозиторію
        verify(participantRepo, times(1)).findAll();
    }
}
