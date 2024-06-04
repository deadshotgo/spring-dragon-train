package com.example.dragon.integration;


import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql("/sql/test_participant_controller/data_test.sql")
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
public class ParticipantControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("GET /api/v1/participants - success request")
    void handleGetAllParticipants_ReturnValidResponseEntity() throws Exception {
        //given
        var requestBuilder = get("/api/v1/participants");
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.length()").value(1),
                        jsonPath("$[0].id").exists(),
                        jsonPath("$[0].name").value("participant 1"),
                        jsonPath("$[0].results").exists()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("GET /api/v1/participants/1 - success request")
    @Transactional
    void handleGetParticipantById_ReturnValidResponseEntity() throws Exception {

        var requestBuilder = get("/api/v1/participants/1");

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(1),
                        jsonPath("$.name").value("participant 1"),
                        jsonPath("$.results").exists()
                );

    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("GET /api/v1/participants/120 - invalid request")
    void handleGetParticipantById_ReturnNotFoundException() throws Exception {

        var requestBuilder = get("/api/v1/participants/120");

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.error").exists()
                );

    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/participants/ success request")
    void handlePostParticipant_ReturnValidResponseEntity() throws Exception {
        var requestBuilder = post("/api/v1/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "participant 2"
                        }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                            {
                            "name": "participant 2"
                        }
                        """),
                        jsonPath("$.id").exists()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/participants/ invalid request")
    void handlePostParticipant_ReturnErrorEntity() throws Exception {
        var requestBuilder = post("/api/v1/participants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": " "
                        }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.errors.name").exists()
                );
    }


    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("DELETE /api/v1/participants/1 success request")
    @Transactional
    void handleDeleteParticipants_ReturnValidResponse() throws Exception {
        var requestBuilder = delete("/api/v1/participants/1")
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }
}
