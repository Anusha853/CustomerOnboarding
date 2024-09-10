/*
package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.PlansService;
import com.bootcamp.customer.Onboarding.model.PlanType;
import com.bootcamp.customer.Onboarding.model.Plans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(PlansController.class)
public class PlansControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlansService plansService;

    private Plans plan;

    @BeforeEach
    public void setUp() {
        plan = new Plans();
        plan.setPlanId(1L);
        plan.setPlan_name("Basic Plan");
        plan.setPlan_description("This is a basic plan.");
        plan.setPrice(99.99);
        plan.setValidity_days(30);
        plan.setPlan_type(new PlanType(1L, "Type A","Plan Description"));
    }

    @Test
    public void testGetAllPlans() throws Exception {
        List<Plans> plansList = Arrays.asList(plan);
        when(plansService.getAllPlans()).thenReturn(plansList);

        mockMvc.perform(get("/user/plans").with(httpBasic("username", "password")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].planId").value(plan.getPlanId()))
                .andExpect(jsonPath("$[0].plan_name").value(plan.getPlan_name()))
                .andExpect(jsonPath("$[0].plan_description").value(plan.getPlan_description()))
                .andExpect(jsonPath("$[0].price").value(plan.getPrice()))
                .andExpect(jsonPath("$[0].validity_days").value(plan.getValidity_days()))
                .andExpect(jsonPath("$[0].plan_type.id").value(plan.getPlan_type().getPlan_type_id()))
                .andExpect(jsonPath("$[0].plan_type.name").value(plan.getPlan_type().getPlan_type_name()))
                .andExpect(jsonPath("$.plan_type.description").value(plan.getPlan_type().getPlan_type_description()));
    }

    @Test
    public void testGetPlanById() throws Exception {
        when(plansService.getPlanById(1L)).thenReturn(plan);

        mockMvc.perform(get("/user/plans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planId").value(plan.getPlanId()))
                .andExpect(jsonPath("$.plan_name").value(plan.getPlan_name()))
                .andExpect(jsonPath("$.plan_description").value(plan.getPlan_description()))
                .andExpect(jsonPath("$.price").value(plan.getPrice()))
                .andExpect(jsonPath("$.validity_days").value(plan.getValidity_days()))
                .andExpect(jsonPath("$.plan_type.id").value(plan.getPlan_type().getPlan_type_id()))
                .andExpect(jsonPath("$.plan_type.name").value(plan.getPlan_type().getPlan_type_name()))
                .andExpect(jsonPath("$.plan_type.description").value(plan.getPlan_type().getPlan_type_description()));
    }

    @Test
    public void testGetPlanById_NotFound() throws Exception {
        when(plansService.getPlanById(1L)).thenReturn(null);

        mockMvc.perform(get("/user/plans/1"))
                .andExpect(status().isNotFound());
    }
}*/
