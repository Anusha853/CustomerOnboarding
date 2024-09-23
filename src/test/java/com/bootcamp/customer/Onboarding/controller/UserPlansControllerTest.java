package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.UserPlansService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserPlansControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserPlansService userPlansService;

    @InjectMocks
    private UserPlansController userPlansController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userPlansController).build();
    }

    @Test
    public void testAddPlansToUser() throws Exception {
        Long userId = 1L;
        List<Long> plansIds = Arrays.asList(1L, 2L, 3L);

        doNothing().when(userPlansService).addPlansToUser(userId, plansIds);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/user-plans/addPlans")
                        .param("userId", userId.toString())
                        .param("plansIds", "1", "2", "3"))
                .andDo(result -> {
                    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
                });

        verify(userPlansService).addPlansToUser(userId, plansIds);
    }

    @Test
    public void testAddPlanToUser() throws Exception {
        Long userId = 1L;
        Long planId = 1L;

        when(userPlansService.addPlanToUser(userId, planId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/user-plans/add")
                        .param("userId", userId.toString())
                        .param("planId", planId.toString()))
                .andDo(result -> {
                    assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
                    assertEquals("Plan added successfully.", result.getResponse().getContentAsString());
                });

        verify(userPlansService).addPlanToUser(userId, planId);
    }

    @Test
    public void testAddPlanToUser_AlreadyExists() throws Exception {
        Long userId = 1L;
        Long planId = 1L;

        when(userPlansService.addPlanToUser(userId, planId)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/user-plans/add")
                        .param("userId", userId.toString())
                        .param("planId", planId.toString()))
                .andDo(result -> {
                    assertEquals(HttpStatus.CONFLICT.value(), result.getResponse().getStatus());
                    assertEquals("Plan already associated with the user.", result.getResponse().getContentAsString());
                });

        verify(userPlansService).addPlanToUser(userId, planId);
    }

    @Test
    public void testAddPlanToUser_UserNotFound() throws Exception {
        Long userId = 1L;
        Long planId = 1L;

        when(userPlansService.addPlanToUser(userId, planId)).thenThrow(new RuntimeException("User Not Found"));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/user-plans/add")
                        .param("userId", userId.toString())
                        .param("planId", planId.toString()))
                .andDo(result -> {
                    assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
                    assertEquals("User Not Found", result.getResponse().getContentAsString());
                });

        verify(userPlansService).addPlanToUser(userId, planId);
    }

}