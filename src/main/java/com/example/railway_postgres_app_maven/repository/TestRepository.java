package com.example.railway_postgres_app_maven.repository;

import com.example.railway_postgres_app_maven.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
}

