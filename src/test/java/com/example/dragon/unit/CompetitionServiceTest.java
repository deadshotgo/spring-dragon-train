package com.example.dragon.unit;

import com.example.dragon.dto.competition.RequestCompetition;
import com.example.dragon.dto.competition.ResponseCompetition;
import com.example.dragon.entity.CompetitionEntity;
import com.example.dragon.repository.CompetitionRepo;
import com.example.dragon.service.CompetitionService;
import jakarta.persistence.EntityNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompetitionServiceTest {

    @Mock
    private CompetitionRepo competitionRepo;

    @InjectMocks
    private CompetitionService competitionService;

    @Test
    public void testGetCompetitions() {
        // Arrange
        CompetitionEntity competition1 = new CompetitionEntity(1L, "Competition 1", LocalDate.now().toString(), LocalDate.now().toString(), Collections.emptyList());
        CompetitionEntity competition2 = new CompetitionEntity(2L, "Competition 2", LocalDate.now().toString(), LocalDate.now().toString(), Collections.emptyList());
        when(competitionRepo.findAll()).thenReturn(Arrays.asList(competition1, competition2));

        // Act
        List<ResponseCompetition> result = competitionService.getCompetitions();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Competition 1", result.get(0).getName());
        assertEquals("Competition 2", result.get(1).getName());
    }

    @Test
    public void testGetCompetition_WhenIdExists() {
        // Arrange
        Long id = 1L;
        CompetitionEntity competition = new CompetitionEntity(id, "Competition", LocalDate.now().toString(), LocalDate.now().toString(), Collections.emptyList());
        when(competitionRepo.findById(id)).thenReturn(Optional.of(competition));

        // Act
        ResponseCompetition result = competitionService.getCompetition(id);

        // Assert
        assertEquals("Competition", result.getName());
    }

    @Test
    public void testGetCompetition_WhenIdNotExists() {
        // Arrange
        Long id = 1L;
        when(competitionRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> competitionService.getCompetition(id));
    }

    @Test
    public void testCreateCompetition() {
        // Arrange
        RequestCompetition request = new RequestCompetition("New Competition", LocalDate.now().toString(), LocalDate.now().toString());
        CompetitionEntity createdCompetition = new CompetitionEntity(1L, "New Competition", LocalDate.now().toString(), LocalDate.now().toString(), Collections.emptyList());
        when(competitionRepo.save(any(CompetitionEntity.class))).thenReturn(createdCompetition);

        // Act
        ResponseCompetition result = competitionService.createCompetition(request);

        // Assert
        assertEquals("New Competition", result.getName());
    }

    @Test
    public void testDeleteCompetition_WhenIdExists() {
        // Arrange
        Long id = 1L;
        // Act
        Long result = competitionService.deleteCompetition(id);

        // Assert
        assertEquals(id, result);
        verify(competitionRepo, times(1)).deleteById(id);
    }
}
