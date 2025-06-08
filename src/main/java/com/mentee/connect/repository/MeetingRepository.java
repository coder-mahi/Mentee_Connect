package com.mentee.connect.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mentee.connect.entity.Meeting;

@Repository
public interface MeetingRepository extends MongoRepository<Meeting, String> {
    List<Meeting> findByMentorId(String mentorId);
    List<Meeting> findByMenteesContaining(String menteeId);
} 