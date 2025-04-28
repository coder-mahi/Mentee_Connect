package com.mahesh.mentee_connect.repository;

import com.mahesh.mentee_connect.model.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BatchRepository extends MongoRepository<Batch, String> {
    // Additional query methods can be added if needed
}
