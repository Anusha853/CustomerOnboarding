package com.bootcamp.customer.Onboarding.controller;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.bootcamp.customer.Onboarding.model.PlanType;
import com.bootcamp.customer.Onboarding.Service.PlansService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class PlansControllerTest {

    @InjectMocks
    private PlansController plansController;

    @Mock
    private PlansService plansService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPlans_Success() {
        Plans plan1 = new Plans(); // Populate with appropriate values
        Plans plan2 = new Plans();
        List<Plans> plansList = Arrays.asList(plan1, plan2);

        when(plansService.getAllPlans()).thenReturn(plansList);

        List<Plans> result = plansController.getAllPlans();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(plansService, times(1)).getAllPlans();
    }

    @Test
    public void testGetPlanById_Found() {
        Long planId = 1L;
        Plans plan = new Plans(); // Populate with appropriate values
        when(plansService.getPlanById(planId)).thenReturn(plan);

        ResponseEntity<Plans> response = plansController.getPlanById(planId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(plan, response.getBody());
        verify(plansService, times(1)).getPlanById(planId);
    }

    @Test
    public void testGetPlanById_NotFound() {
        Long planId = 1L;
        when(plansService.getPlanById(planId)).thenReturn(null);

        ResponseEntity<Plans> response = plansController.getPlanById(planId);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(plansService, times(1)).getPlanById(planId);
    }

    @Test
    public void testGetAllPlanTypes_Success() {
        PlanType type1 = new PlanType(); // Populate with appropriate values
        PlanType type2 = new PlanType();
        List<PlanType> planTypesList = Arrays.asList(type1, type2);

        when(plansService.getAllPlanTypes()).thenReturn(planTypesList);

        List<PlanType> result = plansController.getAllPlanTypes();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(plansService, times(1)).getAllPlanTypes();
    }

    @Test
    public void testGetAllPlanTypes_Empty() {
        when(plansService.getAllPlanTypes()).thenReturn(Collections.emptyList());

        List<PlanType> result = plansController.getAllPlanTypes();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(plansService, times(1)).getAllPlanTypes();
    }
}