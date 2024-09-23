package com.bootcamp.customer.Onboarding.service;

import com.bootcamp.customer.Onboarding.Repository.AdhaarRepository;
import com.bootcamp.customer.Onboarding.Service.AdhaarService;
import com.bootcamp.customer.Onboarding.model.AdhaarData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class AdhaarServiceTest {

    @InjectMocks
    private AdhaarService adhaarService;

    @Mock
    private AdhaarRepository adhaarRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsAadhaarExists_ReturnsTrue() {
        String aadhaarNumber = "1234 5678 9012";
        when(adhaarRepository.existsByAadhaarNumber(aadhaarNumber)).thenReturn(true);

        boolean exists = adhaarService.isAadhaarExists(aadhaarNumber);

        assertTrue(exists);
        verify(adhaarRepository, times(1)).existsByAadhaarNumber(aadhaarNumber);
    }

    @Test
    public void testIsAadhaarExists_ReturnsFalse() {
        String aadhaarNumber = "1234 5678 9012";
        when(adhaarRepository.existsByAadhaarNumber(aadhaarNumber)).thenReturn(false);

        boolean exists = adhaarService.isAadhaarExists(aadhaarNumber);

        assertFalse(exists);
        verify(adhaarRepository, times(1)).existsByAadhaarNumber(aadhaarNumber);
    }

    @Test
    public void testGetAadhaar_Found() {
        Long id = 1L;
        AdhaarData adhaarData = new AdhaarData();
        adhaarData.setId(id);
        when(adhaarRepository.findById(id)).thenReturn(Optional.of(adhaarData));

        AdhaarData result = adhaarService.getAadhaar(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(adhaarRepository, times(1)).findById(id);
    }

    @Test
    public void testGetAadhaar_NotFound() {
        Long id = 1L;
        when(adhaarRepository.findById(id)).thenReturn(Optional.empty());

        AdhaarData result = adhaarService.getAadhaar(id);

        assertNull(result);
        verify(adhaarRepository, times(1)).findById(id);
    }
}