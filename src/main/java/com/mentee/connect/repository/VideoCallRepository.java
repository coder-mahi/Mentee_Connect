package com.mentee.connect.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mentee.connect.entity.VideoCall;

@Repository
public interface VideoCallRepository extends MongoRepository<VideoCall, String> {
    List<VideoCall> findByStatus(String status);
    VideoCall findByMeetingId(String meetingId);
} 