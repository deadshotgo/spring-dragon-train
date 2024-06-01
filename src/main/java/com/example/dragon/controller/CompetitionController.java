package com.example.dragon.controller;

import com.example.dragon.model.Competition;
import com.example.dragon.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/competitions")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;

    @GetMapping
    public List<Competition> getAllCompetitions() {
        return competitionService.getCompetitions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competition> getCompetitionById(@PathVariable Long id) {
        Competition competition = competitionService.getCompetition(id);
        return competition != null ? ResponseEntity.ok(competition) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Competition> createCompetition(@RequestBody Competition competition) {
        Competition createdCompetition = competitionService.createCompetition(competition);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompetition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCompetition(@PathVariable Long id) {
        return ResponseEntity.ok(competitionService.deleteCompetition(id));
    }
}