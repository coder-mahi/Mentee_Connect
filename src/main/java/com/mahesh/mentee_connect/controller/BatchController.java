package com.mahesh.mentee_connect.controller;

import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mahesh.mentee_connect.model.Batch;
import com.mahesh.mentee_connect.payload.request.BatchMentorAssignRequest;
import com.mahesh.mentee_connect.service.BatchService;

@RestController
@RequestMapping("/api/batches")
@PreAuthorize("hasRole('ADMIN')")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @PostMapping
    public ResponseEntity<Batch> createBatch(@RequestBody Batch batch) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(batchService.createBatch(batch));
    }

    @GetMapping
    public ResponseEntity<Page<Batch>> getAllBatches(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(batchService.getAllBatches(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Batch> updateBatch(
            @PathVariable String id,
            @RequestBody Batch batch) {
        return ResponseEntity.ok(batchService.updateBatch(id, batch));
    }

    @PostMapping("/{id}/assign-mentor")
    public ResponseEntity<Batch> assignMentorToBatch(
            @PathVariable String id,
            @RequestBody BatchMentorAssignRequest request) {
        return ResponseEntity.ok(
                batchService.assignMentorToBatch(id, request.getMentorId()));
    }
}