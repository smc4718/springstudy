package com.gdu.app13.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app13.service.FileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FileController {
  
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
    return fileService.ajaxUpload(multipartRequest);
  }
 }