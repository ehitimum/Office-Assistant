package com.example.leave_management.domain.repository;

import com.example.leave_management.domain.model.User.Balance.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {
}