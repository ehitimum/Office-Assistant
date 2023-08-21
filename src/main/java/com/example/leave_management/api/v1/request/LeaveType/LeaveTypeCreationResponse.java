package com.example.leave_management.api.v1.request.LeaveType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.CodePointBuffer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveTypeCreationResponse {
    private String msg;
}
