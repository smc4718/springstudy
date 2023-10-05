package com.gdu.app06.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app06.service.IBoardService;

@RequestMapping("/board")
@Controller
public class BoardController {

  private IBoardService iBoardService;

  @Autowired
  public BoardController(IBoardService iBoardService) {
    super();
    this.iBoardService = iBoardService;
  }
  
  @RequestMapping(value="/list.do", method=RequestMethod.GET)
  public String list(Model model) {
   model.addAttribute("boardList", iBoardService.getBoardList());
   return "board/list"; // 실제로 처리되는 경로는 " /WEB-INF/views/board/list.jsp " 이다.
  }
  
  @RequestMapping(value="/detail.do", method=RequestMethod.GET) // required = false : 혹시 전달되지 않더라도 오류를 내지 마라.
  public String detail(@RequestParam(value="boardNo", required = false, defaultValue="0") int boardNo  // int boardNo 가 전달되는 파라미터를 받는 변수이다.
                     , Model model) {
     model.addAttribute("board", iBoardService.getBoardByNo(boardNo));
     return "board/detail"; // 실제로 처리되는 경로는 "/WEB-INF/views/board/detail.jsp " 이다.
  }
  
  
  
  
}
