package com.example.dragon.controller;

import com.example.dragon.dto.competition.ResponseCompetition;
import com.example.dragon.dto.competition.RequestCompetition;
import com.example.dragon.service.CompetitionService;
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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResponseCompetitionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CompetitionService competitionService;

    @InjectMocks
    private CompetitionController competitionController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(competitionController).build();
    }

    @Test
    void getCompetitions() throws Exception {
        ResponseCompetition responseCompetition = new ResponseCompetition();
        responseCompetition.setId(1L);
        responseCompetition.setName("cs:go");
        responseCompetition.setDateStart("12-12-2003");
        responseCompetition.setDateEnd("13-12-2003");

        ResponseCompetition responseCompetition2 = new ResponseCompetition();
        responseCompetition2.setId(2L);
        responseCompetition2.setName("Defense of the Ancients");
        responseCompetition2.setDateStart("12-12-2003");
        responseCompetition2.setDateEnd("13-12-2003");

        when(competitionService.getCompetitions()).thenReturn(Arrays.asList(responseCompetition, responseCompetition2));

        MvcResult response = mockMvc.perform(get("/competitions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        List<ResponseCompetition> results = objectMapper.readValue(responseBody, new TypeReference<List<ResponseCompetition>>() {});

        assertThat(results).hasSize(2);
        verify(competitionService, times(1)).getCompetitions();
    }

    @Test
    void getCompetition() throws Exception {
        ResponseCompetition responseCompetitionResponse = new ResponseCompetition();
        responseCompetitionResponse.setId(1L);
        responseCompetitionResponse.setName("test");
        responseCompetitionResponse.setDateStart("12-12-2003");
        responseCompetitionResponse.setDateEnd("13-12-2003");

        when(competitionService.getCompetition(1L)).thenReturn(responseCompetitionResponse);
        mockMvc.perform(get("/competitions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(responseCompetitionResponse.getName())))
                .andExpect(jsonPath("$.dateStart", is(responseCompetitionResponse.getDateStart())))
                .andExpect(jsonPath("$.dateEnd", is(responseCompetitionResponse.getDateEnd())));

        verify(competitionService, times(1)).getCompetition(1L);
    }

    @Test
    void createCompetition() throws Exception {
        RequestCompetition requestCompetition = new RequestCompetition();
        requestCompetition.setName("CS:GO");
        requestCompetition.setDateStart("12-12-2003");
        requestCompetition.setDateEnd("13-12-2003");

        ResponseCompetition responseCompetitionResponse = new ResponseCompetition();
        responseCompetitionResponse.setId(1L);
        responseCompetitionResponse.setName(requestCompetition.getName());
        responseCompetitionResponse.setDateStart(requestCompetition.getDateStart());
        responseCompetitionResponse.setDateEnd(requestCompetition.getDateEnd());


        when(competitionService.createCompetition(requestCompetition)).thenReturn(responseCompetitionResponse);

        MvcResult response = mockMvc.perform(post("/competitions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestCompetition)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();

        ResponseCompetition created = objectMapper.readValue(responseBody, ResponseCompetition.class);

        assertNotNull(created);
    }

}
