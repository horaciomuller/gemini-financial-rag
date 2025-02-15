package dev.java10x.SpringRag.core;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RagEngine {

    private final PdfReader pdfReader;
    private final GeminiClient geminiClient;

    public RagEngine(PdfReader pdfReader, GeminiClient geminiClient) {
        this.pdfReader = pdfReader;
        this.geminiClient = geminiClient;
    }

    public String answerQuestion(String question) throws IOException, InterruptedException {
        String pdfText = pdfReader.readPdf();
        String prompt = String.format("Context: %s\nQuestion: %s\nAnswer:", pdfText, question);
        return geminiClient.askGemini(prompt);
    }
}