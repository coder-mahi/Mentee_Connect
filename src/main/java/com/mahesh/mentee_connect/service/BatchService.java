package com.mahesh.mentee_connect.service;
import java.util.*;
import com.mahesh.mentee_connect.model.*;
public interface BatchService {
    Batch saveBatch(Batch batch);
    List<Batch> getAllBatches();
    Batch getBatchById(Long id);
    Batch updateBatch(Long id, Batch batch);
    void deleteBatch(Long id);
}
