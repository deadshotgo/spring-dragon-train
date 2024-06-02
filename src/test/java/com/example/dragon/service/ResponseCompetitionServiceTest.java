package com.example.dragon.service;

import com.example.dragon.dto.competition.ResponseCompetition;
import com.example.dragon.entity.CompetitionEntity;
import com.example.dragon.entity.ParticipantEntity;
import com.example.dragon.entity.ResultEntity;
import com.example.dragon.dto.competition.RequestCompetition;
import com.example.dragon.repository.CompetitionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ResponseCompetitionServiceTest {

    @Mock
    private CompetitionRepo competitionRepo;

    @InjectMocks
    private CompetitionService competitionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCompetition() {
        // Створення тестового об'єкта CompetitionRequest
        RequestCompetition requestCompetition = new RequestCompetition();
        requestCompetition.setName("Football");

        // Очікуване значення для збереження в репозиторії
        CompetitionEntity savedCompetitionEntity = new CompetitionEntity();
        savedCompetitionEntity.setId(1L);
        savedCompetitionEntity.setName(requestCompetition.getName());

        // Налаштування мока репозиторію
        when(competitionRepo.save(any(CompetitionEntity.class))).thenReturn(savedCompetitionEntity);

        // Виклик методу сервісу для створення змагання
        ResponseCompetition createdResponseCompetition = competitionService.createCompetition(requestCompetition);

        // Перевірка результатів
        assertNotNull(createdResponseCompetition, "Створене змагання не повинно бути null");
        assertEquals(1L, createdResponseCompetition.getId(), "ID створеного змагання має бути 1");
        assertEquals(requestCompetition.getName(), createdResponseCompetition.getName(), "Ім'я створеного змагання має бути 'Football'");

        // Перевірка виклику методу репозиторію
        verify(competitionRepo, times(1)).save(any(CompetitionEntity.class));
    }

    @Test
    void getCompetition() {
        ResultEntity resultEntity = getResultEntity();
        // Створення тестового об'єкта CompetitionEntity
        CompetitionEntity competitionEntity = new CompetitionEntity();
        competitionEntity.setId(1L);
        competitionEntity.setName("Football");
        competitionEntity.setResults(new ArrayList<>());
        competitionEntity.getResults().add(resultEntity);

        // Налаштування мока репозиторію для повернення змагання по ID
        when(competitionRepo.findById(1L)).thenReturn(Optional.of(competitionEntity));

        // Виклик методу сервісу для отримання змагання по ID
        ResponseCompetition response = competitionService.getCompetition(1L);

        // Перевірка результатів
        assertNotNull(response, "Отримане змагання не повинно бути null");
        assertEquals(competitionEntity.getId(), response.getId(), "ID отриманого змагання має бути 1");
        assertEquals(competitionEntity.getName(), response.getName(), "Ім'я отриманого змагання має бути 'Football'");

        // Перевірка виклику методу репозиторію
        verify(competitionRepo, times(1)).findById(1L);
    }

    @Test
    void getCompetitions() {
        ResultEntity resultEntity = getResultEntity();
        // Створення тестових об'єктів CompetitionEntity
        CompetitionEntity competitionEntity1 = new CompetitionEntity();
        competitionEntity1.setId(1L);
        competitionEntity1.setName("Football");
        competitionEntity1.setResults(new ArrayList<>());
        competitionEntity1.getResults().add(resultEntity);

        CompetitionEntity competitionEntity2 = new CompetitionEntity();
        competitionEntity2.setId(2L);
        competitionEntity2.setName("Basketball");
        competitionEntity2.setResults(new ArrayList<>());
        competitionEntity2.getResults().add(resultEntity);

        // Налаштування мока репозиторію для повернення списку змагань
        when(competitionRepo.findAll()).thenReturn(Arrays.asList(competitionEntity1, competitionEntity2));

        // Виклик методу сервісу для отримання списку змагань
        List<ResponseCompetition> responseCompetitions = competitionService.getCompetitions();

        // Перевірка результатів
        assertNotNull(responseCompetitions, "Список змагань не повинен бути null");
        assertEquals(2, responseCompetitions.size(), "Розмір списку змагань має бути 2");

        ResponseCompetition responseCompetition1 = responseCompetitions.get(0);
        assertEquals(1L, responseCompetition1.getId(), "ID першого змагання має бути 1");
        assertEquals("Football", responseCompetition1.getName(), "Ім'я першого змагання має бути 'Football'");

        ResponseCompetition responseCompetition2 = responseCompetitions.get(1);
        assertEquals(2L, responseCompetition2.getId(), "ID другого змаг");
    }

    private static ResultEntity getResultEntity() {
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
        return resultEntity;
    }
}
