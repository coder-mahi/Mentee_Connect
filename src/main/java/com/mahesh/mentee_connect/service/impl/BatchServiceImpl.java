package com.mahesh.mentee_connect.service.impl;

import com.mahesh.mentee_connect.model.Batch;
import com.mahesh.mentee_connect.repository.*;
import com.mahesh.mentee_connect.service.BatchService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import java.util.List;

@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public Batch saveBatch(Batch batch) {
        return batchRepository.save(batch);
    }

    @Override
    public List<Batch> getAllBatches() {
        return batchRepository.findAll();
    }

    @Override
    public Batch getBatchById(Long id) {
        return batchRepository.findById(id).orElse(null);
    }

    @Override
    public Batch updateBatch(Long id, Batch batch) {
        batch.setId(id);
        return batchRepository.save(batch);
    }

    @Override
    public void deleteBatch(Long id) {
        batchRepository.deleteById(id);
    }
}

