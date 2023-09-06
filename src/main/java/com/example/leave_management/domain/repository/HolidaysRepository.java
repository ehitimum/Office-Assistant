package com.example.leave_management.domain.repository;

import com.example.leave_management.domain.model.Holidays.Holidays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HolidaysRepository extends JpaRepository<Holidays, Long> {
    @Query(value = "SELECT uh.holiday_id, h.* " +
            "FROM user_holidays uh " +
            "INNER JOIN holidays h ON uh.holiday_id = h.holiday_id " +
            "WHERE uh.user_id = :userId " +
            "ORDER BY uh.holiday_id ASC", nativeQuery = true)
    List<Object[]> findHolidayIdsByUserId(@Param("userId") Long userId);

}
