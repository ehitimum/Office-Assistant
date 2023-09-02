package com.example.leave_management.service.Bills;

import com.example.leave_management.domain.model.Bills.Bills;
import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import com.example.leave_management.domain.model.User.Balance.LeaveBalance;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.BillingRepository;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.dto.Auth.UserDTO;
import com.example.leave_management.dto.Bills.BillsDTO;
import com.example.leave_management.dto.Bills.UtilityRequest;
import com.example.leave_management.dto.Bills.UtilityResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingService {
    private final BillingRepository billingRepository;
    private final UserRepository userRepository;
    public BillingService(BillingRepository billingRepository, UserRepository userRepository) {
        this.billingRepository = billingRepository;
        this.userRepository = userRepository;
    }

    public UtilityResponse applyForUtilityBills(Long userId, UtilityRequest request) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        var bills = Bills.builder()
                .billingTitle(request.getBillingTitle())
                .comment(request.getComment())
                .billCost(request.getBillCost())
                .user(user)
                .billStatus(request.getBillStatus())
                .build();
        billingRepository.save(bills);
        return UtilityResponse.builder().response("Success!").build();
    }

    public List<BillsDTO> showAllPendingBillingRequest(int pageNumber){
        var pageSize = 5;
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<Bills> allPendingApplications = billingRepository.findPendingApplicationsByUserId(pageRequest);
        String message = "Successfully retrieved user list with pagination.";
        return allPendingApplications.getContent().stream()
                .map(this::convertToBillsDTO)
                .collect(Collectors.toList());
    }

    private BillsDTO convertToBillsDTO(Bills bills) {
        return BillsDTO.builder()
                .billId(bills.getBillId())
                .billingTitle(bills.getBillingTitle())
                .comment(bills.getComment())
                .billCost(bills.getBillCost())
                .billStatus(bills.getBillStatus())
                .user(userBuild(bills.getUser()))
                .build();
    }
    public UserDTO userBuild(User user){
        LeaveBalance leaveBalance = user.getLeaveBalance();
        int sickLeaveBalance = leaveBalance != null ? leaveBalance.getSickLeaveBalance() : 0;
        int earnedLeaveBalance = leaveBalance != null ? leaveBalance.getEarnedLeaveBalance() : 0;
        int negativeBalance = leaveBalance != null ? leaveBalance.getNegativeBalance() : 0;
        return UserDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .role(user.getRole())
                .sickLeaveBalance(sickLeaveBalance)
                .earnedLeaveBalance(earnedLeaveBalance)
                .negativeBalance(negativeBalance)
                .build();
    }


    

}
