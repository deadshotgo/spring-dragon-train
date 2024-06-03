package com.example.dragon.controller;

import com.example.dragon.dto.participant.ResponseParticipant;
import com.example.dragon.dto.participant.RequestParticipant;
import com.example.dragon.service.ParticipantService;
import com.example.dragon.swagger.GenerateApiDoc;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/participants")
@Tag(name = "Participants", description = "Endpoints for participant management")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;


    @GenerateApiDoc(
            summary = "Get all participants",
            description = "Retrieve a list of all participants",
            responseDescription = "List of participants retrieved successfully",
            responseClass = ResponseParticipant.class
    )
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<ResponseParticipant> getAllParticipants() {
        return participantService.getParticipants();
    }

    @GenerateApiDoc(
            summary = "Get participant by ID",
            description = "Retrieve a participant by its unique identifier",
            responseDescription = "Participant retrieved successfully by ID",
            responseClass = ResponseParticipant.class
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseParticipant> getParticipantById(@PathVariable Long id) {
        ResponseParticipant responseParticipant = participantService.getParticipant(id);
        return ResponseEntity.ok(responseParticipant);
    }

    @GenerateApiDoc(
            summary = "Create a new participant",
            description = "Create a new participant with the provided data",
            responseDescription = "Participant created successfully",
            responseClass = ResponseParticipant.class
    )
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseParticipant> createParticipant(@Valid @RequestBody RequestParticipant participant) {
        ResponseParticipant createdResponseParticipant = participantService.createParticipant(participant);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponseParticipant);
    }

    @GenerateApiDoc(
            summary = "Delete participant by ID",
            description = "Delete a participant by its unique identifier",
            responseDescription = "Participant deleted successfully",
            responseClass = Long.class
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> deleteParticipant(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.deleteParticipant(id));
    }
}