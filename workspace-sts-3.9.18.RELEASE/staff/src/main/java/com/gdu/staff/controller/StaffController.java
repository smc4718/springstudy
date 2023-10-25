package com.gdu.staff.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.staff.dto.StaffDto;
import com.gdu.staff.service.StaffService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class StaffController {

  private final StaffService staffService;
  
  // 사원 등록 요청
  @PostMapping(value = "/add.do", produces = "application/json")
  public ResponseEntity<Map<String, Object>> add(StaffDto staff) {
     ResponseEntity<Map<String, Object>> responseEntity = staffService.registerStaff(staff);
     
   // 사원 등록이 성공했을 때, 목록을 다시 조회
   if (responseEntity.getStatusCode() == HttpStatus.OK) {
       // 추가 코드: 사원 목록을 조회
       List<StaffDto> staffList = staffService.getStaffList();
       responseEntity.getBody().put("staffList", staffList);
     }
  
     return responseEntity;
  }
  
  // 사원 목록 조회 요청
  @GetMapping(value = "/getStaffList.do", produces = "application/json")
  public ResponseEntity<Map<String, Object>> getStaffList() {
     Map<String, Object> response = new HashMap<>();
     List<StaffDto> staffList = staffService.getStaffList();
     response.put("staffList", staffList);
     return new ResponseEntity<>(response, HttpStatus.OK);
  }

  //사원 조회 요청
  @GetMapping(value = "/getStaff.do", produces = "application/json")
  public ResponseEntity<Map<String, Object>> getStaff(@RequestParam String sno) {
     Map<String, Object> response = new HashMap<>();
     StaffDto staff = staffService.getStaff(sno);
  
     if (staff != null) {
         response.put("staff", staff);
         return new ResponseEntity<>(response, HttpStatus.OK);
     } else {
         return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
     }
}

}
