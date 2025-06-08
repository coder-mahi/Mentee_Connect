package com.mentee.connect.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mentee.connect.entity.ChatMessage;
import com.mentee.connect.service.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/messages")
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessageRequest request) {
        ChatMessage message = new ChatMessage();
        message.setMeetingId(request.getMeetingId());
        message.setSenderId(request.getSenderId());
        message.setMessage(request.getMessage());
        message.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok(chatService.sendMessage(message));
    }

    @GetMapping("/messages/{meetingId}")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String meetingId) {
        return ResponseEntity.ok(chatService.getChatHistory(meetingId));
    }

    private static class ChatMessageRequest {
        private String meetingId;
        private String senderId;
        private String message;
        public String getMeetingId() { return meetingId; }
        public void setMeetingId(String meetingId) { this.meetingId = meetingId; }
        public String getSenderId() { return senderId; }
        public void setSenderId(String senderId) { this.senderId = senderId; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
} 