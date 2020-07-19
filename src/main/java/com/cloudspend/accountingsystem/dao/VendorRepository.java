package com.cloudspend.accountingsystem.dao;

import com.cloudspend.accountingsystem.model.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VendorRepository extends MongoRepository<Vendor, String> {

    boolean existsByName(String name);
    Vendor getByName(String name);
}
