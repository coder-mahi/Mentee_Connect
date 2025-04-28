package com.mahesh.mentee_connect.controller;

import com.mahesh.mentee_connect.model.Batch;
import com.mahesh.mentee_connect.model.Response;
import com.mahesh.mentee_connect.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/batches")
public class BatchController {

    @Autowired
    private BatchService batchService;

    // Create a new batch
    @PostMapping
    public Response createBatch(@RequestBody Batch batch) {
        return batchService.createBatch(batch);
    }

    // Get all batches
    @GetMapping
    public List<Batch> getAllBatches() {
        return batchService.getAllBatches();
    }

    // Get a batch by ID
    @GetMapping("/{id}")
    public Optional<Batch> getBatchById(@PathVariable String id) {
        return batchService.getBatchById(id);
    }
}
