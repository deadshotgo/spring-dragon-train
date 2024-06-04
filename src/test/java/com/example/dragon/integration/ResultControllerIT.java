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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Sql("/sql/test_result_controller/data_test.sql")
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
public class ResultControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("GET /api/v1/results - success request")
    void handleGetAllResults_ReturnValidResponseEntity() throws Exception {
        //given
        var requestBuilder = get("/api/v1/results");
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.length()").value(1),
                        jsonPath("$[0].id").exists(),
                        jsonPath("$[0].score").exists(),
                        jsonPath("$[0].competition").exists(),
                        jsonPath("$[0].participant").exists()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("GET /api/v1/results/1 - success request")
    @Transactional
    void handleGetResultById_ReturnValidResponseEntity() throws Exception {

        var requestBuilder = get("/api/v1/results/1");

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(1),
                        jsonPath("$.score").value("50"),
                        jsonPath("$.competition").exists(),
                        jsonPath("$.participant").exists()
                );

    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("GET /api/v1/results/120 - invalid request")
    void handleGetResultById_ReturnNotFoundException() throws Exception {

        var requestBuilder = get("/api/v1/results/120");

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.error").exists()
                );

    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/results/ success request")
    void handlePostResult_ReturnValidResponseEntity() throws Exception {
        var requestBuilder = post("/api/v1/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "score": "75",
                            "competitionId": 1,
                            "participantId": 1
                        }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").exists(),
                        jsonPath("$.score").value("75"),
                        jsonPath("$.competition.id").value(1),
                        jsonPath("$.participant.id").value(1)
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/results/ invalid request")
    void handlePostResult_ReturnErrorEntity() throws Exception {
        var requestBuilder = post("/api/v1/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "score": " ",
                            "competitionId": " ",
                            "participantId": " "
                        }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.errors.score").exists(),
                        jsonPath("$.errors.competitionId").exists(),
                        jsonPath("$.errors.participantId").exists()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("DELETE /api/v1/results/1 success request")
    @Transactional
    void handleDeleteResults_ReturnValidResponse() throws Exception {
        var requestBuilder = delete("/api/v1/results/1")
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }
}
