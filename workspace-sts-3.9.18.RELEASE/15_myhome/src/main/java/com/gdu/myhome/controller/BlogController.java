package com.gdu.myhome.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.myhome.dto.BlogDto;
import com.gdu.myhome.service.BlogService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/blog")
@RequiredArgsConstructor
@Controller
public class BlogController {

  private final BlogService blogService;
  
  @GetMapping("/list.do")
  public String list(HttpServletRequest request, Model model) {
    blogService.loadBlogList(request, model);
    return "blog/list";
  }
  
  @GetMapping("/write.form")
  public String write() {
    return "blog/write";
  }
  
  // produces 가 있으면, 무조건 @ResponseBody 가 붙는다 (ajax처럼 동작하기 때문) 
  // 첨부 파일 관련된 것은 전부 다 POST 다. (주소창에 파일 못 붙임)
  // json을 반환할 때 최적화된 것 : Map
  @ResponseBody
  @PostMapping(value="/imageUpload.do", produces="application/json") // value만 작성할 때는 value를 생략할 수 있지만, 또 적을 게 있다면 value를 꼭 적어주어야 함.
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest) {   // MultipartHttpServletRequest : 파일을 받을 수 있는 MultipartHttpServletRequest 
    return blogService.imageUpload(multipartRequest);
  }
  
  @PostMapping("/addBlog.do")
  public String addBlog(HttpServletRequest request, RedirectAttributes redirectAttributes) {
    int addResult = blogService.addBlog(request);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/blog/list.do";
  }
  
  @GetMapping("/increaseHit.do")
  public String increaseHit(@RequestParam(value="blogNo", required=false, defaultValue="0") int blogNo) {
  int increaseResult = blogService.increaseHit(blogNo);
  if(increaseResult == 1) {
    return "redirect:/blog/detail.do?blogNo=" + blogNo; // 위에서 조회수 증가가 나오고, 여기서 상세보기가 나온다.
  } else {
    return "redirect:/blog/list.do";
  }
  }
  
  @GetMapping("/detail.do")
  public String detail(@RequestParam(value="blogNo", required=false, defaultValue="0") int blogNo
                     , Model model) {
    BlogDto blog = blogService.getBlog(blogNo);
    model.addAttribute("blog", blog);
    return "blog/detail";  // 내가 작성한 블로그는 조회수가 늘지 않도록 detail로 보내기.  // 내가 작성하지 않은 블로그는 조회수가 늘도록 increaseHit 로 보내기.
  }

  @ResponseBody
  @PostMapping(value="/addComment.do", produces="application/json")
  public Map<String, Object> addComment(HttpServletRequest request) {
    return blogService.addComment(request);
  }
  
  @ResponseBody
  @GetMapping(value="/commentList.do", produces="application/json")
  public Map<String, Object> commentList(HttpServletRequest request){
    return blogService.loadCommentList(request);
  }
  
  
  
  

}
