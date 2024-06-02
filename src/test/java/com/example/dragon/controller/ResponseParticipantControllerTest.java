package com.example.dragon.controller;

import com.example.dragon.dto.participant.ResponseParticipant;
import com.example.dragon.dto.participant.RequestParticipant;
import com.example.dragon.service.ParticipantService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResponseParticipantControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ParticipantService participantService;

    @InjectMocks
    private ParticipantController participantController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(participantController).build();
    }

    @Test
    void getParticipant() throws Exception {
        ResponseParticipant responseParticipant = new ResponseParticipant();
        responseParticipant.setId(1L);
        responseParticipant.setName("test");

        when(participantService.getParticipant(1L)).thenReturn(responseParticipant);
        mockMvc.perform(get("/participants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("test")));

        verify(participantService, times(1)).getParticipant(1L);
    }

    @Test
    void getParticipants() throws Exception {
        ResponseParticipant responseParticipant = new ResponseParticipant();
        responseParticipant.setId(1L);
        responseParticipant.setName("test");

        ResponseParticipant responseParticipant2 = new ResponseParticipant();
        responseParticipant2.setId(2L);
        responseParticipant2.setName("test2");

        when(participantService.getParticipants()).thenReturn(Arrays.asList(responseParticipant, responseParticipant2));

        MvcResult response = mockMvc.perform(get("/participants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();
        List<ResponseParticipant> results = objectMapper.readValue(responseBody, new TypeReference<List<ResponseParticipant>>() {});

        assertThat(results).hasSize(2);
        verify(participantService, times(1)).getParticipants();
    }

    @Test
    void createParticipant() throws Exception {
        RequestParticipant participant = new RequestParticipant();
        participant.setName("test");

        ResponseParticipant responseParticipantResponse = new ResponseParticipant();
        responseParticipantResponse.setId(1L);
        responseParticipantResponse.setName("test");


        when(participantService.createParticipant(participant)).thenReturn(responseParticipantResponse);

        MvcResult response = mockMvc.perform(post("/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participant)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = response.getResponse().getContentAsString();

        ResponseParticipant createdResponseParticipant = objectMapper.readValue(responseBody, ResponseParticipant.class);

        assertNotNull(createdResponseParticipant);
    }
}
