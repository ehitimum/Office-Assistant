package com.example.leave_management.dto.RequestAndResponseDTO.HolidayReqRes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewHolidayResponse {
    private String msg;
}
