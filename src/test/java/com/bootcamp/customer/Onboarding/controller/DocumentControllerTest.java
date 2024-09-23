package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.DocumentService;
import com.bootcamp.customer.Onboarding.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;

    public class DocumentControllerTest {

        @InjectMocks
        private DocumentController documentController;

        @Mock
        private DocumentService documentService;

        @Mock
        private MultipartFile file;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testUploadDocument_Success() throws Exception {
            Long userId = 1L;
            when(file.getOriginalFilename()).thenReturn("document.pdf");
            when(documentService.processDocument(file, userId)).thenReturn("Document Uploaded Successfully!!");

            ResponseEntity<String> response = documentController.uploadDocument(file, userId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Document Uploaded Successfully!! Document Uploaded Successfully!!", response.getBody());
        }

        @Test
        public void testUploadDocument_Failure() throws Exception {
            Long userId = 1L;
            when(file.getOriginalFilename()).thenReturn("document.pdf");
            when(documentService.processDocument(file, userId)).thenThrow(new IOException("File not found"));

            ResponseEntity<String> response = documentController.uploadDocument(file, userId);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("Error processing file: File not found", response.getBody());
        }

        @Test
        public void testIsDocumentVerified_Success() {
            Long userId = 1L;
            when(documentService.isDocumentVerified(userId)).thenReturn(true);

            ResponseEntity<Boolean> response = documentController.isDocumentVerified(userId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(true, response.getBody());
        }

        @Test
        public void testDeleteDocument_Success() {
            Long userId = 1L;

            ResponseEntity<String> response = documentController.deleteDocument(userId);

            verify(documentService, times(1)).deleteDocumentByUserId(userId);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Document deleted successfully", response.getBody());
        }

        @Test
        public void testDeleteDocument_NotFound() {
            Long userId = 1L;
            doThrow(new ResourceNotFoundException("Document not found for userId: " + userId)).when(documentService).deleteDocumentByUserId(userId);

            ResponseEntity<String> response = documentController.deleteDocument(userId);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertEquals("Document not found for userId: " + userId, response.getBody());
        }

        @Test
        public void testDeleteDocument_Failure() {
            Long userId = 1L;
            doThrow(new RuntimeException("Unexpected error")).when(documentService).deleteDocumentByUserId(userId);

            ResponseEntity<String> response = documentController.deleteDocument(userId);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals("Error deleting document: Unexpected error", response.getBody());
        }
    }