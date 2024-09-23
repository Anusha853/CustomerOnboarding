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

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserPlansServiceTest {


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

    private User user;
    private Plans plan;
    private UserPlans userPlans;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setUsername("John Doe");

        plan = new Plans();
        plan.setPlan_id(1L);
        plan.setPlan_name("Basic Plan");
        plan.setValidity_days(30);

        userPlans = new UserPlans();
        userPlans.setUser(user);
        userPlans.setPlan(plan);
        userPlans.setActivation_date(new Date());
        userPlans.setExpiration_date(new Date(System.currentTimeMillis() + (plan.getValidity_days() * 24 * 60 * 60 * 1000L)));
    }

    @Test
    public void testAddPlansToUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(userPlansRepository.save(any(UserPlans.class))).thenReturn(userPlans);

        List<Long> planIds = Arrays.asList(1L);
        userPlansService.addPlansToUser(1L, planIds);

        verify(userPlansRepository, times(1)).save(any(UserPlans.class));
    }

    @Test
    public void testAddPlansToUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userPlansService.addPlansToUser(1L, Arrays.asList(1L));
        });
        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    public void testAddPlansToUser_PlanNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userPlansService.addPlansToUser(1L, Arrays.asList(1L));
        });
        assertEquals("Plan not found", exception.getMessage());
    }

    @Test
    public void testAddPlanToUser_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(userPlansRepository.findPlanIdsByUserId(1L)).thenReturn(Arrays.asList());
        when(userPlansRepository.save(any(UserPlans.class))).thenReturn(userPlans);

        boolean result = userPlansService.addPlanToUser(1L, 1L);

        assertTrue(result);
        verify(notificationService).createNotification(1L, 1L);
    }

    @Test
    public void testAddPlanToUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userPlansService.addPlanToUser(1L, 1L);
        });
        assertEquals("User Not Found", exception.getMessage());
    }

    @Test
    public void testAddPlanToUser_PlanNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userPlansService.addPlanToUser(1L, 1L);
        });
        assertEquals("Plan not found", exception.getMessage());
    }

    @Test
    public void testAddPlanToUser_AlreadyExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(plansRepository.findById(1L)).thenReturn(Optional.of(plan));
        when(userPlansRepository.findPlanIdsByUserId(1L)).thenReturn(Arrays.asList(1L));

        boolean result = userPlansService.addPlanToUser(1L, 1L);

        assertFalse(result);
    }

    @Test
    public void testGetUserPlans_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userPlansRepository.findByUser(user)).thenReturn(Optional.of(userPlans));

        Optional<UserPlans> result = userPlansService.getUserPlans(1L);

        assertTrue(result.isPresent());
        assertEquals(userPlans, result.get());
    }

    @Test
    public void testGetUserPlans_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userPlansService.getUserPlans(1L);
        });
        assertEquals("User Not Found", exception.getMessage());
    }
}
