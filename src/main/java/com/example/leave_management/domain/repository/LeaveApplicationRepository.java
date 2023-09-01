package com.example.leave_management.domain.repository;

import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import com.example.leave_management.domain.model.User.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
    @Query("SELECT la FROM LeaveApplication la WHERE la.user.userId = :userId")
    Page<LeaveApplication> findApplicationsByUserId(Long userId, Pageable pageRequest);

    @Query("SELECT la FROM LeaveApplication la WHERE la.leaveStatus = 'PENDING' AND la.user.Deleted = false ")
    Page<LeaveApplication> findPendingApplicationsByUserId(Pageable pageRequest);

    List<LeaveApplication> findByUser(User user);
}