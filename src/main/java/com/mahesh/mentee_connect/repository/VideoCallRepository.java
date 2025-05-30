// 2. Video Call Repository (src/main/java/com/mahesh/mentee_connect/repository/VideoCallRepository.java)
package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.entity.VideoCall;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoCallRepository extends MongoRepository<VideoCall, String> {
    List<VideoCall> findByMentorId(String mentorId);
    List<VideoCall> findByStatus(String status);
    Optional<VideoCall> findByRoomId(String roomId);
    
    @Query("{'participants': ?0}")
    List<VideoCall> findByParticipantsContaining(String menteeId);
    
    @Query("{'scheduledTime': {$gte: ?0, $lte: ?1}}")
    List<VideoCall> findByScheduledTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("{'mentorId': ?0, 'status': {$in: ['SCHEDULED', 'LIVE']}}")
    List<VideoCall> findActiveMeetingsByMentor(String mentorId);
}
