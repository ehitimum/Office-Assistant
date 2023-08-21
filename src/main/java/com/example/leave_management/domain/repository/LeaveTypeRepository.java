package com.example.leave_management.domain.repository;

import com.example.leave_management.domain.model.Leave.LeaveType.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
     Optional<LeaveType> findByLeaveTypeName(String name);
}