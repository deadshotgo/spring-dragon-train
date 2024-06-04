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

@Sql("/sql/test_user_controller/data_test.sql")
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@Transactional
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("GET /api/v1/users - success request")
    void handleGetAllUsers_ReturnValidResponseEntity() throws Exception {
        //given
        var requestBuilder = get("/api/v1/users");
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.length()").value(1),
                        jsonPath("$[0].id").exists(),
                        jsonPath("$[0].username").exists(),
                        jsonPath("$[0].role").exists(),
                        jsonPath("$[0].password").doesNotExist()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("GET /api/v1/users/1 - success request")
    void handleGetUserById_ReturnValidResponseEntity() throws Exception {

        var requestBuilder = get("/api/v1/users/1");

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(1),
                        jsonPath("$.username").value("admin"),
                        jsonPath("$.role").value("ROLE_ADMIN"),
                        jsonPath("$.password").doesNotExist()
                );

    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("GET /api/v1/users/120 - invalid request")
    void handleGetUserById_ReturnNotFoundException() throws Exception {

        var requestBuilder = get("/api/v1/users/120");

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.error").exists()
                );

    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/users/ success request")
    void handlePostUser_WithoutRole_ReturnValidResponseEntity() throws Exception {
        var requestBuilder = post("/api/v1/users")
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
                        jsonPath("$.id").exists(),
                        jsonPath("$.username").value("admin 2"),
                        jsonPath("$.role").value("ROLE_USER"),
                        jsonPath("$.password").doesNotExist()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/users/ success request")
    void handlePostUser_WithRole_ReturnValidResponseEntity() throws Exception {
        var requestBuilder = post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "username": "admin 2",
                            "password": "password",
                            "roles": "ROLE_ADMIN"
                        }
                        """);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").exists(),
                        jsonPath("$.username").value("admin 2"),
                        jsonPath("$.role").value("ROLE_ADMIN"),
                        jsonPath("$.password").doesNotExist()
                );
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("POST /api/v1/users/ invalid request")
    void handlePostUser_ReturnErrorEntity() throws Exception {
        var requestBuilder = post("/api/v1/users")
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

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    @DisplayName("DELETE /api/v1/users/1 success request")
    @Transactional
    void handleDeleteUsers_ReturnValidResponse() throws Exception {
        var requestBuilder = delete("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON)
                );
    }
}
