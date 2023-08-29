package com.example.leave_management.service.LeaveType;

import com.example.leave_management.dto.EntityDTO.LeaveTypeDTO;
import com.example.leave_management.dto.RequestAndResponseDTO.LeaveType.LeaveTypeCreationResponse;
import com.example.leave_management.dto.RequestAndResponseDTO.LeaveType.NewLeaveType;
import com.example.leave_management.domain.model.Leave.LeaveType.LeaveType;
import com.example.leave_management.domain.repository.LeaveTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveTypeService {
    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    public LeaveTypeCreationResponse createNewLeaveType(NewLeaveType request) {
        var nameExists = leaveTypeRepository.findByLeaveTypeName(request.getLeaveTypeName());
        if (nameExists.isEmpty()) {
            var leaveType = LeaveType.builder()
                    .leaveTypeName(request.getLeaveTypeName())
                    .build();
            leaveTypeRepository.save(leaveType);
            return LeaveTypeCreationResponse.builder()
                    .msg("A New Leave Type has been added")
                    .build();
        }
        return LeaveTypeCreationResponse.builder()
                .msg("Leave Type already exists")
                .build();

    }

    public List<LeaveType> showAllLeaveTypeName() {
        return leaveTypeRepository.findAll();
    }
}
