package com.example.leave_management.domain.repository;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
}