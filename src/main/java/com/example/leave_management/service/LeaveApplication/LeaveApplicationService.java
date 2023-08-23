package com.example.leave_management.service.LeaveApplication;

import com.example.leave_management.api.v1.request.LeaveApplication.ApplicationSubmissonResponse;
import com.example.leave_management.api.v1.request.LeaveApplication.LeaveApplicationRequest;
import com.example.leave_management.api.v1.request.LeaveApplication.updateApplicationReq;
import com.example.leave_management.domain.model.Leave.LeaveApplication.LeaveApplication;
import com.example.leave_management.domain.model.Leave.LeaveType.LeaveType;
import com.example.leave_management.domain.model.User.User;
import com.example.leave_management.domain.repository.LeaveApplicationRepository;
import com.example.leave_management.domain.repository.LeaveTypeRepository;
import com.example.leave_management.domain.repository.UserRepository;
import com.example.leave_management.service.LeaveBalance.LeaveBalanceService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
}
