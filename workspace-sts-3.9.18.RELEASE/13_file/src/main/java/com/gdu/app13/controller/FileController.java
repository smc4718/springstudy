package com.gdu.app13.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app13.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FileController {
  
  // 기본 개념 :  Request, Model, redirect 선언은 Controller 에서만 할 수 있다. ( Controller에서 선언하고 다른 곳에 넘겨주는 방식이다. )
  //              Request 선언은 매개변수에서 가능하다.
  
  private final FileService fileService;
  
  @RequestMapping(value="/upload.do", method=RequestMethod.POST)                                                  // HttpServletRequest 로 받을 수 없음.
  public String upload(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes) { // 파일 첨부가 되어 있는 경우엔 MultipartHttpServletRequest 를 써야 한다
    int addResult = fileService.upload(multipartRequest);
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/main.do";
  }
  
  @RequestMapping(value="/ajax/upload.do", method=RequestMethod.POST, produces="application/json")
  @ResponseBody
  public Map<String, Object> ajaxUpload(MultipartHttpServletRequest multipartRequest) {        // json 보낼 거면 Map 써야 한다. (배열 보낼 때는 List 사용하는 것)
    return fileService.ajaxUpload(multipartRequest);   // ↑ 첨부파일을 받는 리퀘스트는 MultipartHttpServletRequest 로 해야 한다. 일반 HttpServletRequest 은 안된다. 그리고 사전에 MultipartResolver가 등록되어 있어야 파일 첨부가 가능하다.
  }
 
  // 편집기의 이미지 업로드 (업로드는 다 POST 로 한다, 편집기로 이미지 첨부하는 순간, 아래 value 주소로 첨부한 이미지를 보내준다. -> 서버에서는 첨부한 이미지를 저장하는 작업을 한다. 편집기가 첨부된 이미지를 확인할 수 있는 주소를 넘겨달라고 요청하고, json방식으로 url을 저장하고 있는 형태로 응답하는 원리이다.)
  @RequestMapping(value="/ckeditor/upload.do", method=RequestMethod.POST, produces="application/json")
  @ResponseBody   // ← ajax을 반환할 수 있도록 꼭 붙여주어야 한다.
  public Map<String, Object> ckeditorUpload(MultipartFile upload, HttpServletRequest request) { // MultipartFile 타입의 이름을 반드시 upload 라고 해야 파일이 받아진다.
    return fileService.ckeditorUpload(upload, request.getContextPath());  // contextPath 넘기는 것 할 줄 알아야 한다.
  }
  
  @RequestMapping(value="/add.do", method=RequestMethod.POST)
  public void add(@RequestParam String contents) {
    System.out.println(contents);
  }
  
  
  
  
  
  
 }