package com.example.dragon.controller;

import com.example.dragon.model.Participant;
import com.example.dragon.service.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;

    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantService.getParticipants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable Long id) {
        Participant participant = participantService.getParticipant(id);
        return participant != null ? ResponseEntity.ok(participant) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Participant> createParticipant(@RequestBody Participant participant) {
        Participant createdParticipant = participantService.createParticipant(participant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdParticipant);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteParticipant(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.deleteParticipant(id));
    }
}