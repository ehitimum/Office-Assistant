package com.example.leave_management.service.Resources;

import com.example.leave_management.domain.model.Resources;
import com.example.leave_management.domain.repository.ResourceRepository;
import com.example.leave_management.dto.Resources.ResourceRequestDTO;
import com.example.leave_management.exception.ApiResponse.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepository repository;

    public ResourceService(ResourceRepository repository) {
        this.repository = repository;
    }

    public ApiResponse<List<Resources>> showAllSharedResources(int pageNumber){
        try {
            var pageSize = 5;
            Pageable pageRequest = PageRequest.of(pageNumber - 1, pageSize);
            Page<Resources> resources = repository.findAll(pageRequest);
            String message = "Successfully retrieved resource list with pagination.";
            return new ApiResponse<>(true, message, HttpStatus.OK.value(),  resources.getContent(), null);
        }catch (Exception e){
            return new ApiResponse<>(true, "Failed!!", HttpStatus.INTERNAL_SERVER_ERROR.value(),  null, List.of(e.getMessage()));
        }
    }

    public ApiResponse<String> shareNewResource(ResourceRequestDTO request) {
        try{
            var resources = Resources.builder()
                    .resourceTitle(request.getResourceTitle())
                    .resourceContent(request.getResourceContent())
                    .build();
            repository.save(resources);
            return new ApiResponse<>(true, "Success!", HttpStatus.OK.value(), null, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }

    }

    @Transactional
    public ApiResponse<String> updateResource(Long resourceId ,ResourceRequestDTO request) {
        try{
            Resources resources = repository.findById(resourceId)
                    .orElseThrow(()-> new RuntimeException("Resource ID does not exists!"));
            resources.setResourceTitle(request.getResourceTitle());
            resources.setResourceContent(request.getResourceContent());
            repository.save(resources);
            return new ApiResponse<>(true, "The Resource Information is Updated!!", HttpStatus.OK.value(), null, null);
        }catch (Exception e){
            return new ApiResponse<>(false, "Failed to update!!", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, List.of(e.getMessage()));
        }

    }
}
