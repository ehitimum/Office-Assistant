package com.example.leave_management.api.v1.controller;

import com.example.leave_management.domain.model.Holidays.Holidays;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayRequest;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayResponse;
import com.example.leave_management.service.Auth.AuthenticationService;
import com.example.leave_management.service.Holiday.HolidayService;
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
    public ResponseEntity<NewHolidayResponse> addNewHolidays(@RequestBody List<Holidays> holidays){
        return ResponseEntity.ok(holidayService.createNewHolidays(holidays));
    }
    @PostMapping("/create-a-holiday")
    public ResponseEntity<NewHolidayResponse> addAHoliday(@RequestBody NewHolidayRequest request){
        return ResponseEntity.ok(holidayService.createOneHoliday(request));
    }
    @GetMapping("/show-HolidayList")
    public List<Holidays> getAllHolidays() {
        return holidayService.getAllHolidays();
    }

    @PutMapping("/update-holiday-info/{holidayId}")
    public ResponseEntity<NewHolidayResponse> updateHoliday(@PathVariable Long holidayId, @RequestBody Holidays updatedHoliday) {
        return ResponseEntity.ok(holidayService.updateHoliday(holidayId, updatedHoliday));
    }

    @PostMapping("/link-holidays/users/{userId}")
    public ResponseEntity<NewHolidayResponse> linkHolidaysToUser(@PathVariable Long userId, @RequestBody List<Holidays> holidays) {
        return ResponseEntity.ok(holidayService.linkHolidaysToUser(userId, holidays));
    }

}
