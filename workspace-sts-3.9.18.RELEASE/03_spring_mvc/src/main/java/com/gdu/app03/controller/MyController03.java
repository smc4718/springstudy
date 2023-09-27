package com.gdu.app03.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController03 {
  
  /*
   * 1. HttpServletRequest request를 이용한 요청 파라미터 처리
   *  1) Java EE 표준 방식이다.
   *  2) 파라미터뿐만 아니라 HttpSession session, String contextPath 와 같은 정보도 꺼낼 수 있으므로 여전히 강력한 도구이다.
   */
  
  // 파라미터 전달하는 첫 번째 방법
  // @RequestMapping("/blog/detail.do") // GET 방식의 method는 생략할 수 있다. value만 작성할 때 "value="부분도 생략할 수 있다. 
  public String blogDetail(HttpServletRequest request, Model model) { // forwarding할 정보는 Model 에 저장한다.
    // ViewResolver의 prefix : /WEB-INF/views/
    // ViewResolver의 suffix : .jsp
    String blogNo = request.getParameter("blogNo");
    model.addAttribute("blogNo", blogNo);
    return "blog/detail";  // /WEB-INF/views/blog/detail.jsp 로 forward 한다.
                           // forward는 request를 전달한다.
  }
  
  /*
   * 2. @RequestParam을 이용한 요청 파라미터 처리
   *  1) 파라미터의 개수가 적은 경우에 유용하다.
   *  2) 주요 메소드
   *    (1) value        : 요청 파라미터의 이름
   *    (2) required     : 요청 파라미터의 필수 여부 (디폴트 true - 요청 파라미터가 없으면 오류 발생)
   *    (3) defaultValue : 요청 파라미터가 없는 경우에 사용할 값
   *  3) @RequestParam을 생략할 수 있다.
   */
  
  // 파라미터 전달하는 두 번째 방법
  @RequestMapping("/blog/detail.do")
  public String blogDetail2(@RequestParam(value="blogNo", required=false, defaultValue="1") int blogNo, Model model) {
    model.addAttribute("blogNo", blogNo);
    return "blog/detail";
  }
  
  
  
  
  
  
  
}
