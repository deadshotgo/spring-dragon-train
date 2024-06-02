package com.example.dragon.controller;

import com.example.dragon.dto.result.ResponseResult;
import com.example.dragon.dto.result.RequestResult;
import com.example.dragon.service.ResultService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
public class ResponseResultControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ResultService resultService;

    @InjectMocks
    private ResultController resultController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(resultController).build();
    }

    @Test
    void createResult() throws Exception {
        RequestResult resultRequest = new RequestResult();
        resultRequest.setScore("10");
        resultRequest.setCompetitionId(1L);
        resultRequest.setParticipantId(1L);

        ResponseResult result = new ResponseResult();
        result.setId(1L);
        result.setScore("10");

        when(resultService.createResult(any(RequestResult.class))).thenReturn(result);

        MvcResult response = mockMvc.perform(post("/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(resultRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();

        ResponseResult createdResult = objectMapper.readValue(responseBody, ResponseResult.class);

        assertNotNull(createdResult);
    }

    @Test
    void getResults() throws Exception {
        ResponseResult result1 = new ResponseResult();
        result1.setId(1L);
        result1.setScore("12");

        ResponseResult result2 = new ResponseResult();
        result2.setId(2L);
        result2.setScore("50");

        when(resultService.getResults()).thenReturn(Arrays.asList(result1, result2));

        MvcResult response = mockMvc.perform(get("/results")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        List<ResponseResult> results = objectMapper.readValue(responseBody, new TypeReference<List<ResponseResult>>() {});

        assertThat(results).hasSize(2);
        verify(resultService, times(1)).getResults();
    }

    @Test
    void getResult() throws Exception {
        ResponseResult result = new ResponseResult();
        result.setId(1L);
        result.setScore("12");

        when(resultService.getResult(1L)).thenReturn(result);

        mockMvc.perform(get("/results/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.score", is("12")));

        verify(resultService, times(1)).getResult(1L);
    }
}
