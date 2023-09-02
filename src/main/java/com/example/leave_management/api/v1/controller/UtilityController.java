package com.example.leave_management.api.v1.controller;

import com.example.leave_management.dto.Bills.AcceptRejectBillRequestDTO;
import com.example.leave_management.dto.Bills.BillsDTO;
import com.example.leave_management.dto.Bills.UtilityRequestDTO;
import com.example.leave_management.dto.Bills.UtilityResponseDTO;
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
    public ResponseEntity<UtilityResponseDTO> applyForUtilityBills(@PathVariable Long userId, @RequestBody UtilityRequestDTO request){
        return ResponseEntity.ok(billingService.applyForUtilityBills(userId, request));
    }
    @GetMapping("/pending-bill-request/{pageId}")
    public ResponseEntity<List<BillsDTO>> getAllPendingBillRequest(
            @PathVariable int pageId) {
        List<BillsDTO> billsDTOS = billingService.showAllPendingBillingRequest(pageId);
        return ResponseEntity.ok(billsDTOS);
    }
    @PutMapping("/pending-bill-request")
    public ResponseEntity<UtilityResponseDTO> allowRejectBillRequests(@RequestBody AcceptRejectBillRequestDTO request){
        return ResponseEntity.ok(billingService.acceptOrRejectBillingRequest(request));
    }
}
