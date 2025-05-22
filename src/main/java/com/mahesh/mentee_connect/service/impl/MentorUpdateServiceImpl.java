package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.MentorUpdate;
import com.mahesh.mentee_connect.repository.MentorUpdateRepository;
import com.mahesh.mentee_connect.service.MentorUpdateService;
import com.mahesh.mentee_connect.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MentorUpdateServiceImpl implements MentorUpdateService {

    @Autowired
    private MentorUpdateRepository mentorUpdateRepository;

    @Override
    @Transactional
    public MentorUpdate createUpdate(MentorUpdate update) {
        update.setCreatedAt(LocalDateTime.now());
        update.setUpdatedAt(LocalDateTime.now());
        return mentorUpdateRepository.save(update);
    }

    @Override
    @Transactional
    public MentorUpdate updateUpdate(String updateId, MentorUpdate updateDetails) {
        MentorUpdate update = getUpdateById(updateId);
        
        // Update fields
        update.setTitle(updateDetails.getTitle());
        update.setContent(updateDetails.getContent());
        update.setStudentIds(updateDetails.getStudentIds());
        update.setAttachmentUrls(updateDetails.getAttachmentUrls());
        update.setImportant(updateDetails.isImportant());
        update.setUpdatedAt(LocalDateTime.now());
        
        return mentorUpdateRepository.save(update);
    }

    @Override
    @Transactional
    public void deleteUpdate(String updateId) {
        MentorUpdate update = getUpdateById(updateId);
        mentorUpdateRepository.delete(update);
    }

    @Override
    @Transactional(readOnly = true)
    public MentorUpdate getUpdateById(String updateId) {
        return mentorUpdateRepository.findById(updateId)
                .orElseThrow(() -> new ResourceNotFoundException("Update", "id", updateId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorUpdate> getUpdatesByMentor(String mentorId) {
        return mentorUpdateRepository.findByMentorId(mentorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorUpdate> getUpdatesByStudent(String studentId) {
        return mentorUpdateRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorUpdate> getImportantUpdates() {
        return mentorUpdateRepository.findByIsImportantTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MentorUpdate> getUpdatesByMentorAndStudent(String mentorId, String studentId) {
        return mentorUpdateRepository.findByMentorIdAndStudentId(mentorId, studentId);
    }
} 