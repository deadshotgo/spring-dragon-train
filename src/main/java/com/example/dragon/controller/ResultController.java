package com.example.dragon.controller;

import com.example.dragon.dto.result.ResponseResult;
import com.example.dragon.dto.result.RequestResult;
import com.example.dragon.service.ResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class ResultController {
    @Autowired
    private ResultService resultService;

    @GetMapping
    public List<ResponseResult> getResults() {
        return resultService.getResults();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseResult> getParticipantById(@PathVariable Long id) {
        ResponseResult result = resultService.getResult(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseResult> createResult(@Valid @RequestBody RequestResult result) {
        ResponseResult createdResult = resultService.createResult(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResult);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> deleteResult(@PathVariable Long id) {
        return ResponseEntity.ok(resultService.deleteResult(id));
    }
}