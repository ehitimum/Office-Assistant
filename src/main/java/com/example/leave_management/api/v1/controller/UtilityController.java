package com.example.leave_management.api.v1.controller;

import com.example.leave_management.dto.Bills.AcceptRejectBillRequestDTO;
import com.example.leave_management.dto.Bills.UtilityRequestDTO;
import com.example.leave_management.service.Bills.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/utility")
@RequiredArgsConstructor
public class UtilityController {
    @Autowired
    private final BillingService billingService;
    @PostMapping("/apply-for-utility-bills/{userId}")
    public ResponseEntity<?> applyForUtilityBills(@PathVariable Long userId, @RequestBody UtilityRequestDTO request){
        return ResponseEntity.ok(billingService.applyForUtilityBills(userId, request));
    }
    @GetMapping("/pending-bill-request/{pageId}")
    public ResponseEntity<?> getAllPendingBillRequest(
            @PathVariable int pageId) {
        return ResponseEntity.ok(billingService.showAllPendingBillingRequest(pageId));
    }
    @PutMapping("/pending-bill-request")
    public ResponseEntity<?> allowRejectBillRequests(@RequestBody AcceptRejectBillRequestDTO request){
        return ResponseEntity.ok(billingService.acceptOrRejectBillingRequest(request));
    }
}
