package com.mentee.connect.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mentee.connect.entity.ChatMessage;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByMeetingIdOrderByTimestampAsc(String meetingId);
} 