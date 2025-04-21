package com.mahesh.mentee_connect.controller;
import com.mahesh.mentee_connect.model.Batch;
import com.mahesh.mentee_connect.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/batches")
public class BatchController {

    @Autowired
    private BatchService batchService;

    @PostMapping
    public @ResponseBody Batch createBatch(@RequestBody Batch batch) {
        return batchService.saveBatch(batch);
    }

    @GetMapping
    public @ResponseBody List<Batch> getAllBatches() {
        return batchService.getAllBatches();
    }

    @GetMapping("/{id}")
    public @ResponseBody Batch getBatch(@PathVariable Long id) {
        return batchService.getBatchById(id);
    }

    @PutMapping("/{id}")
    public @ResponseBody Batch updateBatch(@PathVariable Long id, @RequestBody Batch batch) {
        return batchService.updateBatch(id, batch);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteBatch(@PathVariable Long id) {
        batchService.deleteBatch(id);
        return "Batch with ID " + id + " deleted successfully";
    }
}
