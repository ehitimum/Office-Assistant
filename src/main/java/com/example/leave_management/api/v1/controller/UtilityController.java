package com.example.leave_management.api.v1.controller;

import com.example.leave_management.dto.Bills.UtilityRequest;
import com.example.leave_management.dto.Bills.UtilityResponse;
import com.example.leave_management.service.Bills.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/utility")
@RequiredArgsConstructor
public class UtilityController {
    @Autowired
    private final BillingService billingService;
    @PostMapping("/apply-for-utility-bills/{userId}")
    public ResponseEntity<UtilityResponse> applyForUtilityBills(@PathVariable Long userId, @RequestBody UtilityRequest request){
        return ResponseEntity.ok(billingService.applyForUtilityBills(userId, request));
    }
}
