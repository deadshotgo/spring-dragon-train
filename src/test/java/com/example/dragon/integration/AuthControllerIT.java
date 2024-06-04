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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Sql("/sql/test_auth_controller/data_test.sql")
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/auth/generate-token - success request")
    void handleGenerateTokenAuth_ReturnValidResponseEntity() throws Exception {
        //given
        var requestBuilder = post("/api/v1/auth/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "admin",
                            "password": "password"
                        }
                        """);;
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.token").exists()

                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/auth/generate-token - invalid request")
    void handleGenerateTokenAuth_ReturnExceptionError() throws Exception {
        //given
        var requestBuilder = post("/api/v1/auth/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "",
                            "password": ""
                        }
                        """);;
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.errors.username").exists(),
                        jsonPath("$.errors.password").exists()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/auth/generate-token - invalid request")
    void handleGenerateTokenAuth_ReturnInvalidUser() throws Exception {
        //given
        var requestBuilder = post("/api/v1/auth/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "admin123",
                            "password": "password"
                        }
                        """);;
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().is5xxServerError(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.error").exists()
                );
    }


    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/users/ success request")
    void handlePostCreate_ReturnValidResponseEntity() throws Exception {
        var requestBuilder = post("/api/v1/auth/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "admin 2",
                            "password": "password"
                        }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.token").exists(),
                        jsonPath("$.responseUser.username").value("admin 2"),
                        jsonPath("$.responseUser.role").value("ROLE_USER"),
                        jsonPath("$.responseUser.password").doesNotExist()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/auth/create-user success request")
    void handlePostCreate_ReturnExceptionUserIsAlreadyExist() throws Exception {
        var requestBuilder = post("/api/v1/auth/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "admin",
                            "password": "password"
                        }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isInternalServerError(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.error").exists()
                );
    }


    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/users/ invalid request")
    void handlePostCreate_ReturnErrorEntity() throws Exception {
        var requestBuilder = post("/api/v1/auth/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": " ",
                            "password": " "
                        }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.errors.username").exists(),
                        jsonPath("$.errors.password").exists()
                );
    }

}
