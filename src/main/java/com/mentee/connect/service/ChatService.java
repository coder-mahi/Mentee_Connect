package com.mentee.connect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mentee.connect.entity.ChatMessage;
import com.mentee.connect.repository.ChatMessageRepository;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatMessage sendMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getChatHistory(String meetingId) {
        return chatMessageRepository.findByMeetingIdOrderByTimestampAsc(meetingId);
    }
} 