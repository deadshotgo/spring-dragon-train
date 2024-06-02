package com.example.dragon.controller;

import com.example.dragon.dto.competition.RequestCompetition;
import com.example.dragon.dto.competition.ResponseCompetition;
import com.example.dragon.service.CompetitionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competitions")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;

    @GetMapping
    public List<ResponseCompetition> getAllCompetitions() {
        return competitionService.getCompetitions();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseCompetition> getCompetitionById(@PathVariable Long id) {
        ResponseCompetition responseCompetition = competitionService.getCompetition(id);
        return ResponseEntity.ok(responseCompetition);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseCompetition> createCompetition(@Valid @RequestBody RequestCompetition competition) {
        ResponseCompetition createdResponseCompetition = competitionService.createCompetition(competition);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResponseCompetition);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> deleteCompetition(@PathVariable Long id) {
        return ResponseEntity.ok(competitionService.deleteCompetition(id));
    }
}