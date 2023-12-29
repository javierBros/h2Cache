package com.cache.repository;

import com.cache.model.DataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;

public interface DataRepository extends JpaRepository<DataModel, String> {
    DataModel getById(String id);
}
