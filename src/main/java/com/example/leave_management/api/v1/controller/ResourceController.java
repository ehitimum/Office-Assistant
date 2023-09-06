package com.example.leave_management.api.v1.controller;

import com.example.leave_management.domain.model.Resources;
import com.example.leave_management.dto.Resources.ResourceRequestDTO;
import com.example.leave_management.dto.Resources.ResourceResponseDTO;
import com.example.leave_management.service.Resources.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource")
@RequiredArgsConstructor
public class ResourceController {
    @Autowired
    private final ResourceService resourceService;

    @PostMapping("/share-resources")
    public ResponseEntity<?> shareNewResources(@RequestBody ResourceRequestDTO request){
        return ResponseEntity.ok(resourceService.shareNewResource(request));
    }
    @GetMapping("/show-all-resources/{pageId}")
    public ResponseEntity<?> getAllResources(@PathVariable int pageId) {
        return ResponseEntity.ok(resourceService.showAllSharedResources(pageId));
    }
    @PutMapping("/update-resource-information/{resourceId}")
    public ResponseEntity<?> allowRejectBillRequests(@PathVariable Long resourceId, @RequestBody ResourceRequestDTO request){
        return ResponseEntity.ok(resourceService.updateResource(resourceId, request));
    }

}
