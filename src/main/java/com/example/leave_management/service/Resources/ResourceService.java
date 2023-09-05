package com.example.leave_management.service.Resources;

import com.example.leave_management.domain.model.Bills.Bills;
import com.example.leave_management.domain.model.Resources;
import com.example.leave_management.domain.repository.ResourceRepository;
import com.example.leave_management.dto.Bills.AcceptRejectBillRequestDTO;
import com.example.leave_management.dto.Bills.UtilityResponseDTO;
import com.example.leave_management.dto.Resources.ResourceRequestDTO;
import com.example.leave_management.dto.Resources.ResourceResponseDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepository repository;

    public ResourceService(ResourceRepository repository) {
        this.repository = repository;
    }

    public List<Resources> showAllSharedResources(int pageNumber){
        var pageSize = 5;
        Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<Resources> resources = repository.findAll(pageRequest);
        String message = "Successfully retrieved user list with pagination.";
        return resources.getContent();
    }

    public ResourceResponseDTO shareNewResource(ResourceRequestDTO request) {
        var resources = Resources.builder()
                .resourceTitle(request.getResourceTitle())
                .resourceContent(request.getResourceContent())
                .build();
        repository.save(resources);
        return ResourceResponseDTO.builder().response("Success!").build();
    }

    @Transactional
    public ResourceResponseDTO updateResource(Long resourceId ,ResourceRequestDTO request) {
        Resources resources = repository.findById(resourceId)
                .orElseThrow(()-> new RuntimeException("Resource ID does not exists!"));
        resources.setResourceTitle(request.getResourceTitle());
        resources.setResourceContent(request.getResourceContent());
        repository.save(resources);
        return ResourceResponseDTO.builder().response("The Resource Information is Updated!!").build();
    }
}
