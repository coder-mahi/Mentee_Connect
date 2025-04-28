package com.mahesh.mentee_connect.service;

import com.mahesh.mentee_connect.model.Batch;
import com.mahesh.mentee_connect.repository.BatchRepository;
import com.mahesh.mentee_connect.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    // Create a new batch
    public Response createBatch(Batch batch) {
        Batch savedBatch = batchRepository.save(batch);
        return new Response("Batch Created Successfully", true);
    }

    // Get all batches
    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    // Get batch by ID
    public Optional<Batch> getBatchById(String id) {
        return batchRepository.findById(id);
    }
}
