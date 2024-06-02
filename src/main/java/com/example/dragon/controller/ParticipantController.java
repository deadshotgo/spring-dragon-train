package com.example.dragon.controller;

import com.example.dragon.dto.participant.ResponseParticipant;
import com.example.dragon.dto.participant.RequestParticipant;
import com.example.dragon.service.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<ResponseParticipant> getAllParticipants() {
        return participantService.getParticipants();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseParticipant> getParticipantById(@PathVariable Long id) {
        ResponseParticipant responseParticipant = participantService.getParticipant(id);
        return ResponseEntity.ok(responseParticipant);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseParticipant> createParticipant(@Valid @RequestBody RequestParticipant participant) {
        ResponseParticipant createdResponseParticipant = participantService.createParticipant(participant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponseParticipant);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> deleteParticipant(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.deleteParticipant(id));
    }
}