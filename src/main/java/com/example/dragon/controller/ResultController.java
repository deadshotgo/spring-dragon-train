package com.example.dragon.controller;

import com.example.dragon.dto.result.ResponseResult;
import com.example.dragon.dto.result.RequestResult;
import com.example.dragon.service.ResultService;
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
@RequestMapping("/api/v1/results")
@Tag(name = "Result", description = "Endpoints for result management")
public class ResultController {
    @Autowired
    private ResultService resultService;

    @GetMapping
    public List<ResponseResult> getResults() {
        return resultService.getResults();
    }

    @GenerateApiDoc(
            summary = "Get result by ID",
            description = "Retrieve a result by its unique identifier",
            responseDescription = "Result retrieved successfully by ID",
            responseClass = ResponseResult.class
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseResult> getResultById(@PathVariable Long id) {
        ResponseResult responseResult = resultService.getResult(id);
        return ResponseEntity.ok(responseResult);
    }

    @GenerateApiDoc(
            summary = "Create a new result",
            description = "Create a new result with the provided data",
            responseDescription = "Result created successfully",
            responseClass = ResponseResult.class
    )
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseResult> createResult(@Valid @RequestBody RequestResult result) {
        ResponseResult createdResult = resultService.createResult(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdResult);
    }

    @GenerateApiDoc(
            summary = "Delete result by ID",
            description = "Delete a result by its unique identifier",
            responseDescription = "Result deleted successfully",
            responseClass = Long.class
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> deleteResult(@PathVariable Long id) {
        return ResponseEntity.ok(resultService.deleteResult(id));
    }
}