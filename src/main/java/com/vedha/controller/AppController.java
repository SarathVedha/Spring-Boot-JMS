package com.vedha.controller;

import com.vedha.dto.SampleDTO;
import com.vedha.service.MessageSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
@Tag(name = "App Controller", description = "APIs for sending messages")
public class AppController {

    private final MessageSender messageSender;

    @Operation(summary = "Send a message", description = "Send a message to the ActiveMQ queue")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> sendMessage(@RequestParam("message") String message) {

        messageSender.sendMessage(message);

        return ResponseEntity.ok(Map.of("message", message, "status", "sent"));
    }

    @Operation(summary = "Send a DTO message", description = "Send a DTO message to the ActiveMQ queue")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @PostMapping(value = "/send-dto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> sendDTOMessage(@RequestBody SampleDTO sampleDTO) {

        messageSender.sendDTOMessage(sampleDTO);

        return ResponseEntity.ok(Map.of("dto", sampleDTO, "status", "sent"));
    }

    @Operation(summary = "Send a message to a topic", description = "Send a message to the ActiveMQ topic")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @PostMapping(value = "/send-topic", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> sendTopicMessage(@RequestParam("message") String message) {

        messageSender.sendTopicMessage(message);

        return ResponseEntity.ok(Map.of("message", message, "status", "sent"));
    }

    @Operation(summary = "Send a DTO message to a topic", description = "Send a DTO message to the ActiveMQ topic")
    @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @PostMapping(value = "/send-dto-topic", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> sendDTOTopicMessage(@RequestBody SampleDTO sampleDTO) {

        messageSender.sendDTOTopicMessage(sampleDTO);

        return ResponseEntity.ok(Map.of("dto", sampleDTO, "status", "sent"));
    }
}
