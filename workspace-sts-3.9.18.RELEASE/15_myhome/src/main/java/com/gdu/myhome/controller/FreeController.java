package com.gdu.myhome.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.myhome.service.FreeService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/free")
@RequiredArgsConstructor
@Controller
public class FreeController {

  private final FreeService freeService;
  
  @GetMapping("/write.form")
  public String writeForm() {
    return "free/write";
  }
  
  @PostMapping("/add.do")
  public String add(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int addResult = freeService.addFree(request); // 값을 받아와서  addResult 에 저장.
    redirectAttributes.addFlashAttribute("addResult", addResult); // addResult에 저장된 것을 redirectAttributes 에 저장.
    return "redirect:/free/list.do";  // 저장한 값을 redirect 로 list.do로 보냄.
  }
 
  @GetMapping("/list.do")
  public String list(HttpServletRequest request, Model model) {
    freeService.loadFreeList(request, model);
    return "free/list";
  }
  
  @PostMapping("/addReply.do")
  public String addReply(HttpServletRequest request, RedirectAttributes redirectAttributes) { // addReplyResult 를 저장해서 보내기 위해 리다이렉트를 써준다.
  int addReplyResult = freeService.addReply(request); // 댓글 결과 받아오기.
  redirectAttributes.addFlashAttribute("addReplyResult", addReplyResult); // 댓글 결과를 리다이렉트에 저장하기.
  return "redirect:/free/list.do";  // 리다이렉트해서 리스트로 보내기.
  }
  
  @PostMapping("/remove.do")
  public String remove(@RequestParam(value="freeNo") int freeNo, RedirectAttributes redirectAttributes) {
    int removeResult = freeService.removeFree(freeNo);  // 삭제 결과 받아오기.
    redirectAttributes.addFlashAttribute("removeResult", removeResult); // 삭제 결과를 리다이렉트에 저장하기
    return "redirect:/free/list.do";  // 삭제 후 목록보기로 되돌아가기(리다이렉트)
  }
  
}
