package com.example.leave_management.dto.Bills;

import com.example.leave_management.domain.model.Bills.BillStatus;
import com.example.leave_management.dto.Auth.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillsDTO {
    private Long billId;
    private String billingTitle;
    private String comment;
    private Integer billCost;
    private BillStatus billStatus;
    private Long userId;
    private UserDTO user;
}
