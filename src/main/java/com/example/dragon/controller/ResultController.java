package com.example.dragon.controller;

import com.example.dragon.model.result.Result;
import com.example.dragon.model.result.ResultRequest;
import com.example.dragon.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {
    @Autowired
    private ResultService resultService;

    @GetMapping
    public List<Result> getResults() {
        return resultService.getResults();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Result> getParticipantById(@PathVariable Long id) {
        Result result = resultService.getResult(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Result> createResult(@RequestBody ResultRequest result) {
        Result createdResult = resultService.createResult(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteResult(@PathVariable Long id) {
        return ResponseEntity.ok(resultService.deleteResult(id));
    }
}