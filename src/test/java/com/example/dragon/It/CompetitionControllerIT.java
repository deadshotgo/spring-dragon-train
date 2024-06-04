package com.example.dragon.It;

import com.example.dragon.entity.CompetitionEntity;
import com.example.dragon.entity.ParticipantEntity;
import com.example.dragon.entity.ResultEntity;
import com.example.dragon.repository.CompetitionRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql("/sql/testCompetitionController/data_test.sql")
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
public class CompetitionControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CompetitionRepo repository;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("GET /api/v1/competitions - success request")
    void handleGetAllCompetitions_ReturnValidResponseEntity() throws Exception {
        //given
         var requestBuilder = get("/api/v1/competitions");
        // when
          this.mockMvc.perform(requestBuilder)
        // then
                  .andExpectAll(
                          status().isOk(),
                          content().contentType(MediaType.APPLICATION_JSON),
                          jsonPath("$.length()").value(1),
                          jsonPath("$[0].name").value("competition 1"),
                          jsonPath("$[0].dateStart").value("12-01-2024"),
                          jsonPath("$[0].dateEnd").value("13-02-2024"),
                          jsonPath("$[0].results").exists()
                  )
          ;
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    @DisplayName("GET /api/v1/competitions/1 - success request")
    void handleGetCompetitionById_ReturnValidResponseEntity() throws Exception {
        var requestBuilder = get("/api/v1/competitions/1");
        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                            {
                                "id": 1,
                                "name": "competition 1",
                                "dateStart": "12-01-2024",
                                "dateEnd": "13-02-2024"
                            }
                        """),
                        jsonPath("$.results").exists()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("GET /api/v1/competitions/120 - invalid request")
    void handleGetCompetitionById_ReturnNotFoundException() throws Exception {
        var requestBuilder = get("/api/v1/competitions/120");
        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                            {
                                "error": "Not found competition with id: 120"
                            }
                        """)
                );
    }


    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/competitions/ success request")
    void handlePostCompetition_ReturnValidResponseEntity() throws Exception {
        var requestBuilder = post("/api/v1/competitions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "competition 2",
                            "dateStart": "12-01-2024",
                            "dateEnd": "13-02-2024"
                        }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                            {
                            "name": "competition 2",
                            "dateStart": "12-01-2024",
                            "dateEnd": "13-02-2024"
                        }
                        """),
                        jsonPath("$.id").exists()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/competitions/ invalid request")
    void handlePostCompetition_ReturnErrorException() throws Exception {
        var requestBuilder = post("/api/v1/competitions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "",
                            "dateStart": "",
                            "dateEnd": ""
                        }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.errors.name").exists(),
                        jsonPath("$.errors.dateStart").exists(),
                        jsonPath("$.errors.dateEnd").exists()
                );
    }


}
