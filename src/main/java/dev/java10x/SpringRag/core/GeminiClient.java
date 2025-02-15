package dev.java10x.SpringRag.core;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.java10x.SpringRag.config.GeminiConfig;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class GeminiClient {

    private final String geminiApiKey;
    private final ObjectMapper objectMapper;

    public GeminiClient(GeminiConfig geminiConfig) {
        this.geminiApiKey = geminiConfig.getApiKey();
        this.objectMapper = new ObjectMapper();
    }

    public String askGemini(String prompt) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String encodedPrompt = URLEncoder.encode(prompt, StandardCharsets.UTF_8);

        String requestBody = String.format("""
                {
                  "contents": [{
                    "parts":[{"text": "%s"}]
                    }]
                 }
                """, encodedPrompt);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + geminiApiKey))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return extractAnswer(response.body());
    }

    private String extractAnswer(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode candidates = root.findPath("candidates");

            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode firstCandidate = candidates.get(0);

                JsonNode contentObject = firstCandidate.findPath("content");

                if (contentObject.isObject() && contentObject.has("parts")) {
                    JsonNode parts = contentObject.findPath("parts");
                    if (parts.isArray() && parts.size() > 0) {
                        for (JsonNode part : parts) {
                            if (part.has("text")) {
                                return part.get("text").asText();
                            }
                        }
                    }
                }
            }

            return "Could not extract answer. Unexpected response format.";

        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing response: " + e.getMessage() + "\nRaw Response: " + response;
        }
    }
}