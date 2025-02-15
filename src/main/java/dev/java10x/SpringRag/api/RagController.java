package dev.java10x.SpringRag.api;

import dev.java10x.SpringRag.core.RagEngine;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
public class RagController {

    private final RagEngine ragEngine;

    public RagController(RagEngine ragEngine) {
        this.ragEngine = ragEngine;
    }

    @GetMapping("/answer")
    public String getAnswer(@RequestParam String question) throws IOException, InterruptedException {
        return ragEngine.answerQuestion(question);
    }
}