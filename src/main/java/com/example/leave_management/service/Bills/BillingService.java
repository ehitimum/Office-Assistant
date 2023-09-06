package com.example.leave_management.service.Bills;

import com.example.leave_management.domain.model.Bills.Bills;
import com.example.leave_management.domain.model.User.Balance.LeaveBalance;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.BillingRepository;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.dto.Auth.UserDTO;
import com.example.leave_management.dto.Bills.AcceptRejectBillRequestDTO;
import com.example.leave_management.dto.Bills.BillsDTO;
import com.example.leave_management.dto.Bills.UtilityRequestDTO;
import com.example.leave_management.exception.ApiResponse.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    public ApiResponse<String> applyForUtilityBills(Long userId, UtilityRequestDTO request) {
        try
        {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

            var bills = Bills.builder()
                    .billingTitle(request.getBillingTitle())
                    .comment(request.getComment())
                    .billCost(request.getBillCost())
                    .user(user)
                    .billStatus(request.getBillStatus())
                    .build();
            billingRepository.save(bills);
            return new ApiResponse<>(true, "Success!", HttpStatus.OK.value(), null, null);
        }catch (Exception ex){
            return new ApiResponse<>(false, "Failed to submit the request!", HttpStatus.INTERNAL_SERVER_ERROR.value(),null,  List.of(ex.getMessage()));
        }
    }

    public ApiResponse<List<BillsDTO>>showAllPendingBillingRequest(int pageNumber){
        try{
            var pageSize = 5;
            Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
            Page<Bills> allPendingApplications = billingRepository.findPendingApplicationsByUserId(pageRequest);
            String message = "Successfully retrieved all pending list with pagination.";
            List<BillsDTO> billsDTOS =  allPendingApplications.getContent().stream()
                    .map(this::convertToBillsDTO)
                    .collect(Collectors.toList());
            return new ApiResponse<>(true, message, HttpStatus.OK.value(), billsDTOS, null);

        }catch (Exception ex){
            return new ApiResponse<>(false, "Failed to get the pending requests!", HttpStatus.INTERNAL_SERVER_ERROR.value(),null,  List.of(ex.getMessage()));

        }
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

    @Transactional
    public ApiResponse<String> acceptOrRejectBillingRequest(AcceptRejectBillRequestDTO request) {
        try{
            Bills bills = billingRepository.findById(request.getBillId())
                    .orElseThrow(()-> new RuntimeException("Bill ID does not exists!"));
            bills.setBillStatus(request.getBillStatus());
            bills.setComment(request.getComment());
            billingRepository.save(bills);
            return new ApiResponse<>(true, "The Bills Request is " + request.getBillStatus(), HttpStatus.OK.value(),null,null);
        }catch (Exception ex){
            return new ApiResponse<>(false, "The Bills Request failed to update! ", HttpStatus.OK.value(),null, List.of(ex.getMessage()));

        }


    }


    

}
