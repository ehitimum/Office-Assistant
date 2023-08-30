package com.example.leave_management.dto.LeaveBalance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomBalanceSetter {
    private int sickLeaveBalance;
    private int earnedLeaveBalance;
    private int negativeBalance;
}
