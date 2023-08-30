package com.example.leave_management.api.v1.controller;

import com.example.leave_management.domain.model.Holidays.Holidays;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayRequest;
import com.example.leave_management.dto.HolidayReqRes.NewHolidayResponse;
import com.example.leave_management.service.Holiday.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
