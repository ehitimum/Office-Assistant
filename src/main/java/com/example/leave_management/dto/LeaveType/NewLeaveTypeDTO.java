package com.example.leave_management.dto.LeaveType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewLeaveTypeDTO {
    @NotNull
    private String leaveTypeName;
}
