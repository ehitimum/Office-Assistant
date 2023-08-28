package com.example.leave_management.service.LeaveApplication;

import com.example.leave_management.dto.RequestAndResponseDTO.LeaveApplication.ApplicationSubmissonResponse;
import com.example.leave_management.dto.RequestAndResponseDTO.LeaveApplication.LeaveApplicationRequest;
import com.example.leave_management.dto.RequestAndResponseDTO.LeaveApplication.updateApplicationReq;
import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import com.example.leave_management.domain.model.Leave.LeaveType.LeaveType;
import com.example.leave_management.domain.model.User.Balance.LeaveBalance;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.LeaveApplicationRepository;
import com.example.leave_management.domain.repository.LeaveTypeRepository;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.dto.EntityDTO.LeaveApplicationDTO;
import com.example.leave_management.dto.EntityDTO.UserDTO;
import com.example.leave_management.service.LeaveBalance.LeaveBalanceService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public ApplicationSubmissonResponse saveApplicationRequest(LeaveApplicationRequest request){
        User user = userRepository.findById(request.getUserId())
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

        return ApplicationSubmissonResponse.builder()
                .response("The Applications have been Submitted")
                .build();
    }

    @Transactional
    public ApplicationSubmissonResponse updateApplicationRequest(updateApplicationReq request) {
        LeaveApplication application = repository.findById(request.getLeaveApplicationId())
                .orElseThrow(()->new RuntimeException("Application Id not matched."));
        application.setLeaveStatus(request.getStatus());
        application.setRemarks(request.getRemarks());
        User user = application.getUser();
        Long userId = user.getUserId();
        repository.save(application);
        balanceService.balanceDeduction(userId);

        return ApplicationSubmissonResponse.builder().response("The Application is"+request.getStatus()).build();
    }

    public List<LeaveApplicationDTO> getALlPendingApplications(int pageNumber) {
        var pageSize = 5;
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<LeaveApplication> allPendingApplications = repository.findPendingApplicationsByUserId(pageRequest);
        String message = "Successfully retrieved user list with pagination.";
        return allPendingApplications.getContent().stream()
                .map(this::convertToLeaveApplicationDTO)
                .collect(Collectors.toList());
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


    public List<LeaveApplicationDTO> getAllApplicationsOfAUser(Long id, int pageNumber) {
        var pageSize = 5;
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<LeaveApplication> allPendingApplications = repository.findApplicationsByUserId(id, pageRequest);
        String message = "Successfully retrieved user list with pagination.";
        return allPendingApplications.getContent().stream()
                .map(this::convertToLeaveApplicationDTO)
                .collect(Collectors.toList());
    }
}
