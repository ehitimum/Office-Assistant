package com.example.leave_management.dto.Bills;

import com.example.leave_management.domain.model.Bills.BillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilityRequest {
    private String billingTitle;
    private String comment;
    private Integer billCost;
    private BillStatus billStatus;
}
