package com.example.leave_management.domain.repository;

import com.example.leave_management.domain.model.Resources;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resources, Long> {
}
