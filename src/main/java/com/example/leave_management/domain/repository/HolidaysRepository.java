package com.example.leave_management.domain.repository;

import com.example.leave_management.domain.model.Holidays.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidaysRepository extends JpaRepository<Holidays, Long> {
}
