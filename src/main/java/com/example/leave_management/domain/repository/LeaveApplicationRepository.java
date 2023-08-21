package com.example.leave_management.domain.repository;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
}