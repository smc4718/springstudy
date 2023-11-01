package com.gdu.myhome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.myhome.service.UploadService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/upload")  // /upload 로 시작하는 것들은 다 컨트롤러로 온다.
@RequiredArgsConstructor
@Controller
public class UploadController {

  private final UploadService uploadService;  // 컨트롤러가 사용할 서비스
  
  @GetMapping("/list.do")
  public String list() {
    return "upload/list";
  }
  
  @GetMapping("/write.form")
  public String write() {
    return "upload/write";
  }
}
