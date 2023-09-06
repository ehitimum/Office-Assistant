package com.example.leave_management.dto.HolidayReqRes;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String holidayName;
    @NotNull
    private LocalDate holidayStartDate;
    @NotNull
    private Integer totalHoliday;
}
