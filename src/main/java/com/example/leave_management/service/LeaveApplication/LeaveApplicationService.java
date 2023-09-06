package com.example.leave_management.service.LeaveApplication;

import com.example.leave_management.dto.LeaveApplication.LeaveApplicationRequestDTO;
import com.example.leave_management.dto.LeaveApplication.UpdateApplicationReqDTO;
import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import com.example.leave_management.domain.model.Leave.LeaveType.LeaveType;
import com.example.leave_management.domain.model.User.Balance.LeaveBalance;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.LeaveApplicationRepository;
import com.example.leave_management.domain.repository.LeaveTypeRepository;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.dto.LeaveApplication.LeaveApplicationDTO;
import com.example.leave_management.dto.Auth.UserDTO;
import com.example.leave_management.exception.ApiResponse.ApiResponse;
import com.example.leave_management.service.LeaveBalance.LeaveBalanceService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveApplicationService {
    private final LeaveApplicationRepository repository;
    private final UserRepository userRepository;
    private final LeaveBalanceService balanceService;

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveApplicationService(LeaveApplicationRepository repository, UserRepository userRepository, LeaveBalanceService balanceService, LeaveTypeRepository leaveTypeRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.balanceService = balanceService;
        this.leaveTypeRepository = leaveTypeRepository;
    }

    public ApiResponse<String> saveApplicationRequest(Long userId,LeaveApplicationRequestDTO request){
        try{
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            LeaveType leaveType = leaveTypeRepository.findById(request.getLeaveTypeId())
                    .orElseThrow(() -> new RuntimeException("There is no such Leave Type Available Yet."));
            var application = LeaveApplication.builder()
                    .leave_begin(request.getLeave_begin())
                    .leave_end(request.getLeave_end())
                    .leaveTypeId(leaveType)
                    .leaveStatus(request.getStatus())
                    .remarks(request.getRemarks())
                    .user(user)
                    .build();
            repository.save(application);
            return new ApiResponse<>(true, "New Leave Application Successfully Submitted!!", HttpStatus.OK.value(), null, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Application is failed to submit!", HttpStatus.OK.value(), null, List.of(e.getMessage()));
        }
    }

    @Transactional
    public ApiResponse<String> updateApplicationRequest(UpdateApplicationReqDTO request) {
        try{
            LeaveApplication application = repository.findById(request.getLeaveApplicationId())
                    .orElseThrow(()->new RuntimeException("Application Id not matched."));
            application.setLeaveStatus(request.getStatus());
            application.setRemarks(request.getRemarks());
            User user = application.getUser();
            Long userId = user.getUserId();
            repository.save(application);
            balanceService.balanceDeduction(userId);
            return new ApiResponse<>(true, "The Application is " + request.getStatus(), HttpStatus.OK.value(), null, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed to update application status!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }
    }

    public ApiResponse<List<LeaveApplicationDTO>> getALlPendingApplications(int pageNumber) {
        try{
            var pageSize = 5;
            Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
            Page<LeaveApplication> allPendingApplications = repository.findPendingApplicationsByUserId(pageRequest);
            String message = "Successfully retrieved all pending application list with pagination.";
            return new ApiResponse<>(true, message, HttpStatus.OK.value(),
                    allPendingApplications.getContent().stream()
                    .map(this::convertToLeaveApplicationDTO)
                    .collect(Collectors.toList()), null );

        }catch (Exception ex){
            return new ApiResponse<>(false, "Failed to retrieve! ", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(ex.getMessage()));
        }

    }

    private LeaveApplicationDTO convertToLeaveApplicationDTO(LeaveApplication application) {
        return LeaveApplicationDTO.builder()
                .leaveApplicationId(application.getLeaveApplicationId())
                .leaveBegin(application.getLeave_begin())
                .leaveEnd(application.getLeave_end())
                .leaveTypeId(application.getLeaveTypeId().getLeaveTypeId())
                .leaveTypeName(application.getLeaveTypeId().getLeaveTypeName())
                .leaveStatus(application.getLeaveStatus())
                .remarks(application.getRemarks())
                .userId(application.getUser().getUserId())
                .user(userBuild(application.getUser()))
                .days(ChronoUnit.DAYS.between(application.getLeave_begin(),application.getLeave_end()))
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


    public ApiResponse<List<LeaveApplicationDTO>> getAllApplicationsOfAUser(Long id, int pageNumber) {
        try{
            var pageSize = 5;
            Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
            Page<LeaveApplication> allPendingApplications = repository.findApplicationsByUserId(id, pageRequest);
            String message = "Successfully retrieved all user application records with pagination.";
            List<LeaveApplicationDTO> leaveApplicationDTOS =  allPendingApplications.getContent().stream()
                    .map(this::convertToLeaveApplicationDTO)
                    .collect(Collectors.toList());
            return new ApiResponse<>(true, message, HttpStatus.OK.value(), leaveApplicationDTOS, null);
        }catch (Exception ex){
            return new ApiResponse<>(false, "Failed to retrieve application records!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(ex.getMessage()));
        }


    }
}
