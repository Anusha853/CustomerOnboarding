package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.UserService;
import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserDetailsDTO;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() throws Exception {
        when(userService.checkIfUSerExists("testuser")).thenReturn(false);

        mockMvc.perform(post("/user/register")
                        .param("username", "testuser")
                        .param("password", "password")
                        .param("email", "test@example.com")
                        .param("phone", "1234567890")
                        .param("customerType", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("Registration Successfully"));
    }

    @Test
    public void testRegisterUser_UserExists() throws Exception {
        when(userService.checkIfUSerExists("testuser")).thenReturn(true);

        mockMvc.perform(post("/user/register")
                        .param("username", "testuser")
                        .param("password", "password")
                        .param("email", "test@example.com")
                        .param("phone", "1234567890")
                        .param("customerType", "1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").value("User Already exists"));
    }

    @Test
    public void testRegisterUser_Exception() throws Exception {
        when(userService.checkIfUSerExists("testuser")).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(post("/user/register")
                        .param("username", "testuser")
                        .param("password", "password")
                        .param("email", "test@example.com")
                        .param("phone", "1234567890")
                        .param("customerType", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Error registering user: Database error"));
    }

    @Test
    public void testLoginUser_Success() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        when(userService.loginUser("testuser", "password")).thenReturn(user);

        mockMvc.perform(post("/user/login")
                        .param("username", "testuser")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testLoginUser_InvalidCredentials() throws Exception {
        when(userService.loginUser("testuser", "password")).thenReturn(null);

        mockMvc.perform(post("/user/login")
                        .param("username", "testuser")
                        .param("password", "password"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Invalid username or password"));
    }

    @Test
    public void testProfileUser_Success() throws Exception {
        UserDetailsDTO userDetails = new UserDetailsDTO();
        userDetails.setUsername("testuser");
        when(userService.authenticate("testuser", "password")).thenReturn(userDetails);

        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", "testuser");
        loginRequest.put("password", "password");

        mockMvc.perform(post("/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testProfileUser_InvalidCredentials() throws Exception {
        when(userService.authenticate("testuser", "password")).thenReturn(null);

        mockMvc.perform(post("/user/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\", \"password\":\"password\"}"))
                .andExpect(status().isUnauthorized());
    }

}