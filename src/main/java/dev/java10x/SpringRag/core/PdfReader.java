package dev.java10x.SpringRag.core;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class PdfReader {

    private static final String PDF_FILE_NAME = "petrobras.pdf";

    public String readPdf() throws IOException {
        try {
            ClassPathResource resource = new ClassPathResource(PDF_FILE_NAME);
            File file = resource.getFile();
            try (PDDocument document = PDDocument.load(file)) {
                PDFTextStripper stripper = new PDFTextStripper();
                return stripper.getText(document);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Error reading PDF: " + e.getMessage());
        }
    }
}