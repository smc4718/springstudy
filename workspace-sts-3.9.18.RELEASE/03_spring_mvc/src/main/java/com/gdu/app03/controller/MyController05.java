package com.gdu.app03.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController05 {
  
  /*
   *  redirect 하는 방법
   *  
   *  1. return "redirect:이동경로";
   *     public String add(){
   *       return "redirect:/list.do";
   *     }
   *     
   *  2. location.href='이동경로';  (2번 방법은 alert 창을 띄우기 위해서 씀, 우리는 1번위주로 씀)
   *     public void add(HttpServletResponse response) {
   *       PrintWriter out = response.getWriter();
   *       out.println("<script>");
   *       out.println("location.href='이동경로'");
   *       out.println("<script>");
   *     }
   */

  /*
   * redirect 이동경로
   * 1. 반드시 URLMapping값을 작성한다.
   * 2. 이동할 JSP 경로를 작성할 수 없다.
   */
  
  @RequestMapping(value="/faq/add.do", method=RequestMethod.POST)
  public String add(HttpServletRequest request) {
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    System.out.println(title + "," + content);
    return "redirect:/faq/list.do";
  }                   //     ↓↓↓ 아래 list.do로 이동함.
  
  @RequestMapping(value="/faq/list.do", method=RequestMethod.GET)
  public String list() {
    System.out.println("faq 목록보기");
    return "";    // JSP 경로를 적어서 특정 JSP 로 넘어가게 작성해줌.
  }
  
}