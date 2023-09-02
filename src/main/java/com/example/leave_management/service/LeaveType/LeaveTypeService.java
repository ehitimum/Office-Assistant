package com.example.leave_management.service.LeaveType;

import com.example.leave_management.dto.LeaveType.LeaveTypeCreationResponseDTO;
import com.example.leave_management.dto.LeaveType.NewLeaveTypeDTO;
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

    public LeaveTypeCreationResponseDTO createNewLeaveType(NewLeaveTypeDTO request) {
        var nameExists = leaveTypeRepository.findByLeaveTypeName(request.getLeaveTypeName());
        if (nameExists.isEmpty()) {
            var leaveType = LeaveType.builder()
                    .leaveTypeName(request.getLeaveTypeName())
                    .build();
            leaveTypeRepository.save(leaveType);
            return LeaveTypeCreationResponseDTO.builder()
                    .msg("A New Leave Type has been added")
                    .build();
        }
        return LeaveTypeCreationResponseDTO.builder()
                .msg("Leave Type already exists")
                .build();

    }

    public List<LeaveType> showAllLeaveTypeName() {
        return leaveTypeRepository.findAll();
    }
}
