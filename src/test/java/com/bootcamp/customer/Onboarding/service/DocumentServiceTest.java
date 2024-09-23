package com.bootcamp.customer.Onboarding.service;

import com.bootcamp.customer.Onboarding.Repository.DocumentRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.Service.AdhaarService;
import com.bootcamp.customer.Onboarding.Service.DocumentService;
import com.bootcamp.customer.Onboarding.Service.NotificationService;
import com.bootcamp.customer.Onboarding.exceptions.ResourceNotFoundException;
import com.bootcamp.customer.Onboarding.model.Document;
import com.bootcamp.customer.Onboarding.model.User;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


    public class DocumentServiceTest {

        @Mock
        private DocumentRepository documentRepository;

        @Mock
        private UserRepository userRepository;

        @Mock
        private NotificationService notificationService;

        @Mock
        private AdhaarService adhaarService;

        @Mock
        private ITesseract tesseract;

        @InjectMocks
        private DocumentService documentService;

        private User user;
        private Document document;
        private MultipartFile file;

        @BeforeEach
        public void setUp() throws Exception {
            MockitoAnnotations.openMocks(this);
            user = new User();
            user.setUserId(1L);
            document = new Document();
            document.setUser(user);
            document.setType("Aadhaar");
            document.setStatus(false);
            file = mock(MultipartFile.class);
            when(file.getOriginalFilename()).thenReturn("test.pdf");
            when(file.getBytes()).thenReturn("Test Content".getBytes());
        }



        @Test
        public void testVerifyDocument_NotFound() {
            when(documentRepository.findByUserId(1L)).thenReturn(Optional.empty());

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                documentService.verifyDocument(1L);
            });

            assertEquals("Document not found for userId:1", exception.getMessage());
        }

        @Test
        public void testIsDocumentVerified_Success() {
            when(documentRepository.findByUserId(1L)).thenReturn(Optional.of(document));
            document.setStatus(true);

            boolean isVerified = documentService.isDocumentVerified(1L);

            assertTrue(isVerified);
        }

        @Test
        public void testIsDocumentVerified_NotFound() {
            when(documentRepository.findByUserId(1L)).thenReturn(Optional.empty());

            boolean isVerified = documentService.isDocumentVerified(1L);

            assertFalse(isVerified);
        }

        @Test
        public void testDeleteDocumentByUserId_Success() {
            when(documentRepository.findByUserId(1L)).thenReturn(Optional.of(document));

            documentService.deleteDocumentByUserId(1L);

            verify(documentRepository).delete(document);
        }

        @Test
        public void testDeleteDocumentByUserId_NotFound() {
            when(documentRepository.findByUserId(1L)).thenReturn(Optional.empty());

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                documentService.deleteDocumentByUserId(1L);
            });

            assertEquals("Document not found for userId: 1", exception.getMessage());
        }
    }