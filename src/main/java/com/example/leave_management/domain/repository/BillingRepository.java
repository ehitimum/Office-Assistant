package com.example.leave_management.domain.repository;

import com.example.leave_management.domain.model.Bills.Bills;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BillingRepository extends JpaRepository<Bills, Long> {

    @Query("SELECT bi FROM Bills bi WHERE bi.billStatus = 'PENDING' AND bi.user.Deleted = false")
    Page<Bills> findPendingApplicationsByUserId(Pageable pageRequest);
}
