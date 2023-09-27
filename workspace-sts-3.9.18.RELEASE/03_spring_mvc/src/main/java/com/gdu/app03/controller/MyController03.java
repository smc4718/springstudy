package com.gdu.app03.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController03 {

  @RequestMapping("/blog/detail.do") // GET 방식의 method는 생략할 수 있다. value만 작성할 때 "value="부분도 생략할 수 있다. 
  public String blogDetail(HttpServletRequest request) {
    // ViewResolver의 prefix : /WEB-INF/views/
    // ViewResolver의 suffix : .jsp
    String blogNo = request.getParameter("blogNo");
    request.setAttribute("blogNo", blogNo); // request에 정보 저장하는 코드
    return "blog/detail";  // /WEB-INF/views/blog/detail.jsp 로 forward 한다.
  }                        // forward는 request를 전달한다.
}
