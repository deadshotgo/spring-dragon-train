package com.example.dragon.service;

import com.example.dragon.entity.ParticipantEntity;
import com.example.dragon.model.Participant;
import com.example.dragon.repository.ParticipantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepo participantRepo;

    public List<Participant> getParticipants() {
        List<ParticipantEntity> entities = participantRepo.findAll();
        return entities.stream()
                .map(Participant::toModel)
                .collect(Collectors.toList());
    }

    public Participant getParticipant(Long id) {
        ParticipantEntity entity = participantRepo.findById(id).orElse(null);
        return entity != null ? Participant.toModel(entity) : null;
    }

    public Participant createParticipant(Participant participant) {
        ParticipantEntity entity = new ParticipantEntity();
        entity.setName(participant.getName());
        return Participant.toModel(participantRepo.save(entity));
    }

    public Long deleteParticipant(Long id) {
        participantRepo.deleteById(id);
        return id;
    }
}
