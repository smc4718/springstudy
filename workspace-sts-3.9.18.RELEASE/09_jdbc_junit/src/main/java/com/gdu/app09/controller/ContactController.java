package com.gdu.app09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdu.app09.service.ContactService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor  //  private final ContactService contactService;에 @Autowired를 하기 위한 코드이다.
@Controller
public class ContactController {

  private final ContactService contactService;
  
  @RequestMapping(value="/contact/list.do", method=RequestMethod.GET)
  public String list(Model model) {   // Model 은 포워딩 데이터 전달할 때 쓰는 것.
    model.addAttribute("contactList", contactService.getContactList()); // 목록을 받아와서 contactList란 이름으로 저장했다.
    return "contact/list";
  }
  
  
  
  
  
}
