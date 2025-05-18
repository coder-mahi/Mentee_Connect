package com.mahesh.mentee_connect.controller;

import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mahesh.mentee_connect.model.Batch;
import com.mahesh.mentee_connect.model.Student;
import com.mahesh.mentee_connect.payload.request.BatchMentorAssignRequest;
import com.mahesh.mentee_connect.payload.request.BatchStudentsAssignRequest;
import com.mahesh.mentee_connect.service.BatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/batches")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Tag(name = "Batches", description = "Batch management APIs")
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

    @PostMapping("/{id}/assign-students")
    @Operation(summary = "Assign students to batch", description = "Assign multiple students to a batch (Admin only)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Students successfully assigned to batch"),
        @ApiResponse(responseCode = "404", description = "Batch or Student not found"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<List<Student>> assignStudentsToBatch(
            @PathVariable String id,
            @Valid @RequestBody BatchStudentsAssignRequest request) {
        return ResponseEntity.ok(
                batchService.assignStudentsToBatch(id, request.getStudentIds()));
    }
}