package com.example.dragon.service;

import com.example.dragon.dto.participant.ResponseParticipant;
import com.example.dragon.entity.ParticipantEntity;
import com.example.dragon.dto.participant.RequestParticipant;
import com.example.dragon.repository.ParticipantRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepo participantRepo;

    public List<ResponseParticipant> getParticipants() {
        List<ParticipantEntity> entities = participantRepo.findAll();
        return entities.stream()
                .map(ResponseParticipant::toModel)
                .collect(Collectors.toList());
    }

    public ResponseParticipant getParticipant(Long id) {
        ParticipantEntity entity = participantRepo.findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException("Participant with id " + id + " not found");
        }
        return ResponseParticipant.toModel(entity);
    }

    public ResponseParticipant createParticipant(RequestParticipant participant) {
        try {
            ParticipantEntity entity = new ParticipantEntity();
            entity.setName(participant.getName());
            return ResponseParticipant.toModelReference(participantRepo.save(entity));
        } catch (Exception e) {
          throw new InternalError("Something went wrong");
        }
    }

    public Long deleteParticipant(Long id) {
        try {
            participantRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new InternalError("Something went wrong");
        }
    }
}
