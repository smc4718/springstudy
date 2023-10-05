package com.gdu.app07.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app07.dto.Dto1;
import com.gdu.app07.service.Service1;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AjaxController1 {

  private final Service1 service1;
  
  @ResponseBody   // 메소드의 반환 값이 응답 데이터이다.         // produces : 응답 데이터 타입 (응답하는 데이터가 json이다.)
  @RequestMapping(value="/ajax1/list.do", method=RequestMethod.GET, produces="application/json; charset=UTF-8")
  public List<Dto1> list(){
   return service1.getDtoList();  // jackson 라이브러리가 List<Dto1>를 json 데이터로 자동 변환한다. 
  }
  
  @ResponseBody
  @RequestMapping(value="/ajax1/detail.do", method=RequestMethod.GET)
  public Dto1 detail(@RequestParam(value="name") String name) {
    System.out.println(name);
    return null;
  }
  
  
}
