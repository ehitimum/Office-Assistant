package com.example.leave_management.api.v1.controller;

import com.example.leave_management.domain.model.Holidays.Holidays;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayRequestDTO;
import com.example.leave_management.exception.ApiResponse.ApiResponse;
import com.example.leave_management.service.Holiday.HolidayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/holidays")
@RequiredArgsConstructor
@Validated
public class HolidayController {

    private final HolidayService holidayService;
    @PostMapping("/create-holidays")
    public ResponseEntity<?> addNewHolidays(@Valid @RequestBody List<Holidays> holidays){
        return ResponseEntity.ok(holidayService.createNewHolidays(holidays));
    }
    @PostMapping("/create-a-holiday")
    public ResponseEntity<?> addAHoliday(@Valid @RequestBody NewHolidayRequestDTO request){
        return ResponseEntity.ok(holidayService.createOneHoliday(request));
    }
    @GetMapping("/show-HolidayList")
    public ApiResponse<List<Holidays>> getAllHolidays() {
        return holidayService.getAllHolidays();
    }
    @GetMapping("/show-user-holidayList/{userId}")
    public ResponseEntity<?> getUserHolidayIds(@PathVariable Long userId) {
        return ResponseEntity.ok(holidayService.getHolidayIdsByUserId(userId));
    }

    @PutMapping("/update-holiday-info/{holidayId}")
    public ResponseEntity<?> updateHoliday(@PathVariable Long holidayId, @Valid @RequestBody NewHolidayRequestDTO requestDTO) {
        return ResponseEntity.ok(holidayService.updateHoliday(holidayId, requestDTO));
    }

    @PostMapping("/link")
    public ResponseEntity<?> linkHolidaysToUser(@RequestParam("userId") Long userId, @Valid @RequestBody List<Long> holidayIds) {
        return ResponseEntity.ok(holidayService.linkHolidaysToUser(userId, holidayIds));
    }

}
