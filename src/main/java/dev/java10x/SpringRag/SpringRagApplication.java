package dev.java10x.SpringRag;

import dev.java10x.SpringRag.config.GeminiConfig;
import dev.java10x.SpringRag.core.GeminiClient;
import dev.java10x.SpringRag.core.PdfReader;
import dev.java10x.SpringRag.core.RagEngine;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringRagApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRagApplication.class, args);
	}

	@Bean
	public PdfReader pdfReader() {
		return new PdfReader();
	}

	@Bean
	public GeminiClient geminiClient(GeminiConfig geminiConfig) {
		return new GeminiClient(geminiConfig);
	}

	@Bean
	public RagEngine ragEngine(PdfReader pdfReader, GeminiClient geminiClient) {
		return new RagEngine(pdfReader, geminiClient);
	}
}