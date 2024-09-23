package com.bootcamp.customer.Onboarding.service;


import com.bootcamp.customer.Onboarding.Repository.*;
import com.bootcamp.customer.Onboarding.Service.EmailService;
import com.bootcamp.customer.Onboarding.Service.OtpService;
import com.bootcamp.customer.Onboarding.Service.UserService;
import com.bootcamp.customer.Onboarding.exceptions.ValidationException;
import com.bootcamp.customer.Onboarding.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private OtpService otpService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private UserPlansRepository userPlansRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPhoneNumber("1234567890");
        user.setCustomerType(1);
    }

    @Test
    public void testRegisterUser_Success() {
        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser("testuser", "password", "test@example.com", "1234567890", 1);

        assertNotNull(registeredUser);
        assertEquals("testuser", registeredUser.getUsername());
        verify(emailService).sendEmail("test@example.com", "testuser");
    }

    @Test
    public void testRegisterUser_UsernameRequired() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(null, "password", "test@example.com", "1234567890", 1);
        });
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    public void testRegisterUser_PasswordRequired() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser("testuser", null, "test@example.com", "1234567890", 1);
        });
        assertEquals("Password is required", exception.getMessage());
    }

    @Test
    public void testRegisterUser_EmailRequired() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser("testuser", "password", null, "1234567890", 1);
        });
        assertEquals("Email is required", exception.getMessage());
    }

    @Test
    public void testRegisterUser_CustomerTypeRequired() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser("testuser", "password", "test@example.com", "1234567890", 0);
        });
        assertEquals("Customer type is required", exception.getMessage());
    }

    @Test
    public void testLoginUser_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password", user.getPasswordHash())).thenReturn(true);

        User loggedInUser = userService.loginUser("testuser", "password");

        assertNotNull(loggedInUser);
        assertEquals("testuser", loggedInUser.getUsername());
    }

    @Test
    public void testLoginUser_Failure() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("wrongpassword", user.getPasswordHash())).thenReturn(false);

        User loggedInUser = userService.loginUser("testuser", "wrongpassword");

        assertNull(loggedInUser);
    }

    @Test
    public void testCheckIfUserExists_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        boolean exists = userService.checkIfUSerExists("test@example.com");

        assertTrue(exists);
    }

    @Test
    public void testCheckIfUserExists_Failure() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        boolean exists = userService.checkIfUSerExists("nonexistent@example.com");

        assertFalse(exists);
    }


    @Test
    public void testSendOtp_UserNotFound() {
        when(userRepository.findByUsername("unknownuser")).thenReturn(null);

        boolean result = userService.sendOtp("unknownuser");

        assertFalse(result);
    }

    @Test
    public void testUpdatePassword_Success() {
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        boolean result = userService.updatePassword("testuser", "newPassword");

        assertTrue(result);
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdatePassword_UserNotFound() {
        when(userRepository.findByUsername("unknownuser")).thenReturn(null);

        boolean result = userService.updatePassword("unknownuser", "newPassword");

        assertFalse(result);
    }

    @Test
    public void testAuthenticate_Success() {
        Plans plan = new Plans();
        plan.setPlan_name("Basic Plan");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(passwordEncoder.matches("password", user.getPasswordHash())).thenReturn(true);
        when(userPlansRepository.findPlansByUserId(user.getUserId())).thenReturn(Arrays.asList(plan));

        UserDetailsDTO userDetails = userService.authenticate("testuser", "password");

        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertFalse(userDetails.getPlans().isEmpty());
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        when(userRepository.findByUsername("unknownuser")).thenReturn(null);

        UserDetailsDTO userDetails = userService.authenticate("unknownuser", "password");

        assertNull(userDetails);
    }


    @Test
    public void testGetUsersByDocumentStatus_Success() {
        when(documentRepository.findByStatus(true)).thenReturn(Arrays.asList());

        List<AdminDto> userList = userService.getUsersByDocumentStatus(true);

        assertTrue(userList.isEmpty());
    }

    @Test
    public void testTogglePlanActivation_Success() {
        UserPlans userPlans = new UserPlans();
        userPlans.setActivated(true);

        when(userPlansRepository.findByUser_UserIdAndPlan_PlanId(1L, 1L)).thenReturn(Optional.of(userPlans));
        when(userPlansRepository.save(any(UserPlans.class))).thenReturn(userPlans);

        boolean result = userService.togglePlanActivation(1L, 1L);

        assertTrue(result);
        assertFalse(userPlans.isActivated());
    }

    @Test
    public void testTogglePlanActivation_NotFound() {
        when(userPlansRepository.findByUser_UserIdAndPlan_PlanId(1L, 1L)).thenReturn(Optional.empty());

        boolean result = userService.togglePlanActivation(1L, 1L);

        assertFalse(result);
    }
}