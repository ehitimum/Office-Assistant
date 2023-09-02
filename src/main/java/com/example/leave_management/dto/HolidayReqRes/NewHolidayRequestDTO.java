package com.example.leave_management.dto.HolidayReqRes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewHolidayRequestDTO {
    private String holidayName;
    private LocalDate holidayStartDate;
    private Integer totalHoliday;
}
