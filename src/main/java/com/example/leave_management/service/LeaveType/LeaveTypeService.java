package com.example.leave_management.service.LeaveType;

import com.example.leave_management.dto.LeaveType.NewLeaveTypeDTO;
import com.example.leave_management.domain.model.Leave.LeaveType.LeaveType;
import com.example.leave_management.domain.repository.LeaveTypeRepository;
import com.example.leave_management.exception.ApiResponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeService {
    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    public ApiResponse<String> createNewLeaveType(NewLeaveTypeDTO request) {
        try{
            var nameExists = leaveTypeRepository.findByLeaveTypeName(request.getLeaveTypeName());
            if (nameExists.isEmpty()) {
                var leaveType = LeaveType.builder()
                        .leaveTypeName(request.getLeaveTypeName())
                        .build();
                leaveTypeRepository.save(leaveType);
                return new ApiResponse<>(true, "A new leave type is added!", HttpStatus.OK.value(), null, null);
            }
            return new ApiResponse<>(false, "Leave Type Already Exists!", HttpStatus.CREATED.value(), null, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed To Create the Leave Type!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }
    }

    public List<LeaveType> showAllLeaveTypeName() {
        return leaveTypeRepository.findAll();
    }
}
