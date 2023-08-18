package com.example.leave_management.Models.Entities.Holidays;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Holidays {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long holidayId;
    private String holidayName;
    private LocalDate holidayStartDate;
    private Integer totalHoliday;
}
