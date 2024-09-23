package com.bootcamp.customer.Onboarding.service;


import com.bootcamp.customer.Onboarding.Repository.PlanTypeRepository;
import com.bootcamp.customer.Onboarding.Repository.PlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.Service.PlansService;
import com.bootcamp.customer.Onboarding.model.PlanType;
import com.bootcamp.customer.Onboarding.model.Plans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlansServiceTest {

    @Mock
    private PlansRepository plansRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlanTypeRepository planTypeRepository;

    @InjectMocks
    private PlansService plansService;

    private Plans plan;
    private PlanType planType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        plan = new Plans();
        plan.setPlan_id(1L);
        plan.setPlan_name("Basic Plan");

        planType = new PlanType();
        planType.setPlan_type_id(1L);
        planType.setPlan_type_name("Type A");
    }

    @Test
    public void testGetAllPlanTypes() {
        List<PlanType> planTypes = new ArrayList<>(Arrays.asList(planType));
        when(planTypeRepository.findAll()).thenReturn(planTypes);

        List<PlanType> result = plansService.getAllPlanTypes();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Type A", result.get(0).getPlan_type_name());
        verify(planTypeRepository).findAll();
    }

    @Test
    public void testGetAllPlans() {
        List<Plans> plansList = new ArrayList<>(Arrays.asList(plan));
        when(plansRepository.findAll()).thenReturn(plansList);

        List<Plans> result = plansService.getAllPlans();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Basic Plan", result.get(0).getPlan_name());
        verify(plansRepository).findAll();
    }

    @Test
    public void testGetPlanById_Found() {
        when(plansRepository.findById(1L)).thenReturn(Optional.of(plan));

        Plans result = plansService.getPlanById(1L);

        assertNotNull(result);
        assertEquals("Basic Plan", result.getPlan_name());
        verify(plansRepository).findById(1L);
    }

    @Test
    public void testGetPlanById_NotFound() {
        when(plansRepository.findById(1L)).thenReturn(Optional.empty());

        Plans result = plansService.getPlanById(1L);

        assertNull(result);
        verify(plansRepository).findById(1L);
    }
}