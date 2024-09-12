package com.bootcamp.customer.Onboarding.service;


import com.bootcamp.customer.Onboarding.Repository.PlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserPlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.Service.NotificationService;
import com.bootcamp.customer.Onboarding.Service.UserPlansService;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserPlans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserPlansServiceTest {

    @Mock
    private UserPlansRepository userPlansRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlansRepository plansRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private UserPlansService userPlansService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPlansToUser() {
        Long userId = 1L;
        List<Long> planIds = List.of(1L, 2L);
        User user = new User();
        Plans plan1 = new Plans();
        Plans plan2 = new Plans();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.of(plan1));
        when(plansRepository.findById(2L)).thenReturn(Optional.of(plan2));

        userPlansService.addPlansToUser(userId, planIds);

        verify(userPlansRepository, times(2)).save(any(UserPlans.class));
    }

    @Test
    void testAddPlanToUser() {
        Long userId = 1L;
        Long planId = 1L;
        User user = new User();
        Plans plan = new Plans();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(plansRepository.findById(planId)).thenReturn(Optional.of(plan));

        userPlansService.addPlanToUser(userId, planId);

        verify(userPlansRepository).save(any(UserPlans.class));
        verify(notificationService).createNotification(userId, planId);
    }

    @Test
    void testGetUserPlans() {
        Long userId = 1L;
        User user = new User();
        UserPlans userPlans = new UserPlans();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userPlansRepository.findByUser(user)).thenReturn(userPlans);

        UserPlans result = userPlansService.getUserPlans(userId);

        assertEquals(userPlans, result);
    }

    @Test
    void testAddPlansToUser_UserNotFound() {
        Long userId = 1L;
        List<Long> planIds = List.of(1L, 2L);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userPlansService.addPlansToUser(userId, planIds);
        });

        assertEquals("User Not Found", thrown.getMessage());
    }

    @Test
    void testAddPlanToUser_UserNotFound() {
        Long userId = 1L;
        Long planId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userPlansService.addPlanToUser(userId, planId);
        });

        assertEquals("User Not Found", thrown.getMessage());
    }

    @Test
    void testAddPlanToUser_PlanNotFound() {
        Long userId = 1L;
        Long planId = 1L;
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(plansRepository.findById(planId)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userPlansService.addPlanToUser(userId, planId);
        });

        assertEquals("Plan not found", thrown.getMessage());
    }
}
