package com.gdu.app07.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.app07.dto.AjaxDto;
import com.gdu.app07.service.AjaxService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/ajax3")
@RequiredArgsConstructor
@Controller
public class AjaxController3 {

  private final AjaxService ajaxService;
  
  // ajax3에서 요청을 받는 메소드
  @GetMapping(value="/list.do", produces="application/json; charset=UTF-8")
  public ResponseEntity<List<AjaxDto>> list() {         // ResponseEntity<T> : ajax으로 응답할 때 쓰라고 있는 전용(일종의 Wrapper 감싸개) : List같은 것들같이 여러가지를 싸서 넣을 수 있다
    return new ResponseEntity<List<AjaxDto>>(           // ResponseEntity<T> 는 ResponseBody를 작성하지 않는다.(ResponseBody를 가지고 있기 때문.)
                  ajaxService.getDtoList() // 실제 응답 데이터     // <List<AjaxDto>> list() : ajax list의 반환타입을 AjaxDto로 적어줌.  
                   , HttpStatus.OK);       // 응답 코드 (200)
  }
  
  @PostMapping(value="/detail.do")
  public ResponseEntity<AjaxDto> detail(@RequestBody AjaxDto ajaxDto) { // @RequestBody : 요청 본문에서 찾아라 ( {"name": "뽀로로"} 를 찾을 것임. ) , 그리고 찾은 값을 저장할 수 있는 변수는 Dto가 있으면 Dto로 받고 아니면 대신할 수 있는 Map으로 받으면 된다. 둘중 아무거나 써도 됨.
  // ↑↑↑ Post 방식(@RequestBody)으로 전송된 JSON 데이터(AjaxDto ajaxDto)
  
  // 응답 헤더 : Content-Type ( produces="application/json; charset=UTF-8" 을 대체한다. )
  HttpHeaders header = new HttpHeaders();
  header.add("Content-Type", "application/json; charset=UTF-8");
  
  // 반환
  return new ResponseEntity<AjaxDto>(ajaxService.getDto(
                ajaxDto.getName())    // 실제 응답 데이터
              , header                // 응답 헤더
              , HttpStatus.OK);       // 응답 코드 (200)
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}
