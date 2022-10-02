package com.villa.resident.repository;

import com.villa.resident.model.Resident;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface ResidentRepository extends MongoRepository<Resident, String> {
}
