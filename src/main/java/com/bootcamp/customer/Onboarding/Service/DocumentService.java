package com.bootcamp.customer.Onboarding.Service;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DocumentService {

    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    public String processDocument(MultipartFile file) throws IOException, TesseractException {
        // Save the file to a temporary location
        Path tempFilePath = Paths.get(TEMP_DIR, file.getOriginalFilename());
        Files.write(tempFilePath, file.getBytes());

        // Initialize Tesseract
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Users\\e031759\\Project\\Onboarding\\src\\main\\resources\\TrainingData"); // Adjust path based on your OS and Tesseract installation

        // Perform OCR
        String text = tesseract.doOCR(tempFilePath.toFile());

        // Extract Aadhaar number and name using regex
        String aadhaarNumber = extractAadhaarNumber(text);
        String name = extractName(text);

        // Cleanup
        Files.delete(tempFilePath);

        // Return the extracted information
        return String.format("Aadhaar Number: %s, Name: %s", aadhaarNumber, name);
    }

    private String extractAadhaarNumber(String text) {
        // Regex for Aadhaar number (assuming the format is XXXX XXXX XXXX)
        Pattern pattern = Pattern.compile("\\d{4}\\s\\d{4}\\s\\d{4}");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group() : "Not found";
    }

    private String extractName(String text) {
        // Regex for name extraction (assuming it starts with 'Name:' and is followed by the name)
        Pattern pattern = Pattern.compile("Name\\s*:\\s*(.*)");
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1).trim() : "Not found";
    }
}