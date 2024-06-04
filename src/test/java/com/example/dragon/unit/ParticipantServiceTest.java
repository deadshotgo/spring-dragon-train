package com.example.dragon.unit;

import com.example.dragon.dto.participant.RequestParticipant;
import com.example.dragon.dto.participant.ResponseParticipant;
import com.example.dragon.entity.ParticipantEntity;
import com.example.dragon.repository.ParticipantRepo;
import com.example.dragon.service.ParticipantService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceTest {

    @Mock
    private ParticipantRepo participantRepo;

    @InjectMocks
    private ParticipantService participantService;

    @Test
    void testGetParticipants() {
        // Arrange
        List<ParticipantEntity> mockEntities = new ArrayList<>();
        mockEntities.add(new ParticipantEntity(1L, "Participant 1", Collections.emptyList()));
        mockEntities.add(new ParticipantEntity(2L, "Participant 2", Collections.emptyList()));
        when(participantRepo.findAll()).thenReturn(mockEntities);

        // Act
        List<ResponseParticipant> result = participantService.getParticipants();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Participant 1", result.get(0).getName());
        assertEquals("Participant 2", result.get(1).getName());
    }

    @Test
    void testGetParticipant_WhenExists() {
        // Arrange
        Long id = 1L;
        ParticipantEntity mockEntity = new ParticipantEntity(id, "Participant", Collections.emptyList());
        when(participantRepo.findById(id)).thenReturn(Optional.of(mockEntity));

        // Act
        ResponseParticipant result = participantService.getParticipant(id);

        // Assert
        assertEquals("Participant", result.getName());
    }

    @Test
    void testGetParticipant_WhenNotExists() {
        // Arrange
        Long id = 1L;
        when(participantRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> participantService.getParticipant(id));
    }

    @Test
    void testCreateParticipant() {
        // Arrange
        RequestParticipant request = new RequestParticipant("New Participant");
        ParticipantEntity savedEntity = new ParticipantEntity(1L, "New Participant", Collections.emptyList());
        when(participantRepo.save(any(ParticipantEntity.class))).thenReturn(savedEntity);

        // Act
        ResponseParticipant result = participantService.createParticipant(request);

        // Assert
        assertEquals("New Participant", result.getName());
    }

    @Test
    void testDeleteParticipant_WhenExists() {
        // Arrange
        Long id = 1L;

        // Act
        Long result = participantService.deleteParticipant(id);

        // Assert
        assertEquals(id, result);
        verify(participantRepo, times(1)).deleteById(id);
    }
}
