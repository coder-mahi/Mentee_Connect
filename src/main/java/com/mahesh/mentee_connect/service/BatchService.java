package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.Batch;
import com.mahesh.mentee_connect.repository.BatchRepository;
import com.mahesh.mentee_connect.model.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import com.mahesh.mentee_connect.exception.ResourceNotFoundException;
import com.mahesh.mentee_connect.model.Mentor;
import com.mahesh.mentee_connect.repository.MentorRepository;
import com.mahesh.mentee_connect.repository.StudentRepository;

@Service
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Transactional
    public Batch createBatch(Batch batch) {
        return batchRepository.save(batch);
    }

    @Transactional(readOnly = true)
    public Page<Batch> getAllBatches(int page, int size) {
        return batchRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public Batch assignMentorToBatch(String batchId, String mentorId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch", "id", batchId));
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new ResourceNotFoundException("Mentor", "id", mentorId));
        
        batch.setMentorAssigned(mentorId);
        return batchRepository.save(batch);
    }

    @Transactional(readOnly = true)
    public List<Student> getBatchStudents(String batchId) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch", "id", batchId));
        return studentRepository.findByBatch(batch.getBatchName());
    }

    @Transactional
    public Batch updateBatch(String id, Batch batchDetails) {
        Batch batch = batchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Batch", "id", id));
        
        batch.setBatchName(batchDetails.getBatchName());
        batch.setStartDate(batchDetails.getStartDate());
        batch.setEndDate(batchDetails.getEndDate());
        batch.setCourse(batchDetails.getCourse());
        
        return batchRepository.save(batch);
    }

    @Transactional
    public List<Student> assignStudentsToBatch(String batchId, List<String> studentIds) {
        Batch batch = batchRepository.findById(batchId)
            .orElseThrow(() -> new RuntimeException("Batch not found with id: " + batchId));

        List<Student> students = new ArrayList<>();
        for (String studentId : studentIds) {
            Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
            
            // Update student's batch information
            student.setBatch(batch.getBatchName());
            student = studentRepository.save(student);
            students.add(student);
        }

        return students;
    }
}