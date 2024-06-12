package com.example.dragon.controller;

import com.example.dragon.dto.competition.RequestCompetition;
import com.example.dragon.dto.competition.ResponseCompetition;
import com.example.dragon.service.CompetitionService;
import com.example.dragon.swagger.GenerateApiDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/competitions")
@Tag(name = "Competition", description = "Endpoints for competition management")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;


    @GenerateApiDoc(
            summary = "Get all competitions",
            description = "Retrieve a list of all competitions",
            responseDescription = "List of competitions retrieved successfully",
            responseClass = ResponseCompetition.class
    )

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping
    public List<ResponseCompetition> getAllCompetitions() {
        return competitionService.getCompetitions();
    }


    @GenerateApiDoc(
            summary = "Get competition by ID",
            description = "Retrieve a competition by its unique identifier",
            responseDescription = "Competition retrieved successfully by ID",
            responseClass = ResponseCompetition.class
    )
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCompetition> getCompetitionById(@PathVariable Long id) {
        ResponseCompetition responseCompetition = competitionService.getCompetition(id);
        return ResponseEntity.ok(responseCompetition);
    }


    @GenerateApiDoc(
            summary = "Create a new competition",
            description = "Create a new competition with the provided data",
            responseDescription = "Competition created successfully",
            responseCode = "201",
            responseClass = ResponseCompetition.class
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseCompetition> createCompetition(@Valid @RequestBody RequestCompetition competition) {
        ResponseCompetition createdResponseCompetition = competitionService.createCompetition(competition);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponseCompetition);
    }


    @GenerateApiDoc(
            summary = "Delete competition by ID",
            description = "Delete a competition by its unique identifier",
            responseDescription = "Competition deleted successfully",
            responseClass = Long.class
    )
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCompetition(@PathVariable Long id) {
        return ResponseEntity.ok(competitionService.deleteCompetition(id));
    }
}