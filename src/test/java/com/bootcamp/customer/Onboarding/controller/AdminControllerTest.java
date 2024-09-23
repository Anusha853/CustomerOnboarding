package com.bootcamp.customer.Onboarding.controller;


import com.bootcamp.customer.Onboarding.Service.UserService;
import com.bootcamp.customer.Onboarding.model.AdminDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers_Success() {
        // Arrange
        List<AdminDto> users = Arrays.asList(new AdminDto(
                1L, "user1", "user1@example.com", "1234567890", "Regular",
                "Verified", Arrays.asList(1L), Arrays.asList("Basic Plan"),
                Arrays.asList(true)
        ));
        when(userService.getAllUsers()).thenReturn(users);

        // Act
        ResponseEntity<List<AdminDto>> response = adminController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testGetUsersByType_Success() {
        // Arrange
        List<AdminDto> users = Arrays.asList(new AdminDto(
                1L, "user1", "user1@example.com", "1234567890", "Regular",
                "Verified", Arrays.asList(1L), Arrays.asList("Basic Plan"),
                Arrays.asList(true)
        ));
        when(userService.getUsersByType(anyInt())).thenReturn(users);

        // Act
        ResponseEntity<List<AdminDto>> response = adminController.getUsersByType(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testGetUsersByDocumentStatus_Success() {
        // Arrange
        List<AdminDto> users = Arrays.asList(new AdminDto(
                1L, "user1", "user1@example.com", "1234567890", "Regular",
                "Verified", Arrays.asList(1L), Arrays.asList("Basic Plan"),
                Arrays.asList(true)
        ));
        when(userService.getUsersByDocumentStatus(true)).thenReturn(users);

        // Act
        ResponseEntity<List<AdminDto>> response = adminController.getUsersByDocumentStatus(true);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    @Test
    public void testTogglePlanActivation_Success() {
        // Arrange
        Long userId = 1L;
        Long planId = 1L;
        when(userService.togglePlanActivation(userId, planId)).thenReturn(true);

        // Act
        ResponseEntity<String> response = adminController.togglePlanActivation(userId, planId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Plan activation status updated successfully.", response.getBody());
    }

    @Test
    public void testTogglePlanActivation_Failure() {
        // Arrange
        Long userId = 1L;
        Long planId = 1L;
        when(userService.togglePlanActivation(userId, planId)).thenReturn(false);

        // Act
        ResponseEntity<String> response = adminController.togglePlanActivation(userId, planId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Failed to update plan activation status.", response.getBody());
    }
}