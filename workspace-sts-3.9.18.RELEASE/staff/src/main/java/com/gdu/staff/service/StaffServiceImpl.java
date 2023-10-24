package com.gdu.staff.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gdu.staff.dao.StaffMapper;
import com.gdu.staff.dto.StaffDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StaffServiceImpl implements StaffService {

  private final StaffMapper staffMapper;
  
  @Override
  public ResponseEntity<Map<String, Object>> registerStaff(StaffDto staff) {
    int addResult = 0;
    Map<String, Object> map = new HashMap<>();  // HashMap 은 생성시에 <안에 생략가능>.
    try {
      addResult = staffMapper.insertStaff(staff);
      map.put("addResult", addResult);  // Map.of 로 하지 않은 이유 : addResult 가 값을 받지 못할 수 있어서.(insert 하다가 뻗어버리는 경우 : 1.똑같은 사원번호가 입력될 때, 2.NOT NULL인데 NULL처리 되는 경우 예외가 발생함. 3.사원번호가 5바이트인데 6바이트 넣을 때.
      return new ResponseEntity<>(map, HttpStatus.OK);  // new 뒤에는 '<>'타입 생략가능. // 여기 map은 success 로 간다.
    } catch (Exception e) {
      map.put("addResult", addResult);
      return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR); // 여기 map은 error로 간다.
    }
  }
  
}
