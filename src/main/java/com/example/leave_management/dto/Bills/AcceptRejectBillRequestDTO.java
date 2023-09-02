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
public class AcceptRejectBillRequestDTO {
    private Long billId;
    private String comment;
    private BillStatus billStatus;
}
