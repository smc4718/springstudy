package com.gdu.app10.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app10.dto.ContactDto;
import com.gdu.app10.service.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j  // private static final Logger log = LoggerFactory.getLogger(ContactController.class);
@RequiredArgsConstructor  //  private final ContactService contactService;에 @Autowired를 하기 위한 코드이다.
@Controller
public class ContactController {
  
  // ContactController를 실행할 때 org.slf4j.Logger가 동작한다.
  // private static final Logger log = LoggerFactory.getLogger(ContactController.class);

  private final ContactService contactService;
  
  @RequestMapping(value="/contact/list.do", method=RequestMethod.GET)
  public String list(Model model) {   // Model 은 포워딩 데이터 전달할 때 쓰는 것.
    log.info("");
    List<ContactDto> contactList =  contactService.getContactList();
    model.addAttribute("contactList", contactList); // 목록을 받아와서 contactList란 이름으로 저장했다.
    log.info(contactList.toString());
    return "contact/list";
  }
  
  @RequestMapping(value="/contact/write.do", method=RequestMethod.GET)
  public String write() {
    return "contact/write";
  }
  
  @RequestMapping(value="/contact/add.do", method=RequestMethod.POST)
  public String add(ContactDto contactDto, RedirectAttributes redirectAttributes) {
    log.info("add: " + contactDto);
    int addResult = contactService.addContact(contactDto);
    redirectAttributes.addFlashAttribute("addResult", addResult);  // 그냥 Attribute 는 Model처럼 동작하기 때문에, Flash를 써줘야한다.
    return "redirect:/contact/list.do";   // 목록보기로 다시 넘어가기 (list.do)
  }
  
  @RequestMapping(value="/contact/detail.do", method=RequestMethod.GET)
  public String detail(@RequestParam(value="contact_no", required=false, defaultValue="0") int contact_no, Model model) {
    log.info("detaik:" + contact_no);
    model.addAttribute("contact", contactService.getContactByNo(contact_no));
    return "contact/detail";
  }
  
  @RequestMapping(value="/contact/modify.do", method=RequestMethod.POST)
  public String modify(ContactDto contactDto, RedirectAttributes redirectAttributes) {
    log.info("modify:" + contactDto);
    int modifyResult = contactService.modifyContact(contactDto);
    redirectAttributes.addFlashAttribute("modifyResult", modifyResult);
    return "redirect:/contact/detail.do?contact_no=" + contactDto.getContact_no();  // 수정 후에 상세보기로 가겠다. (상세보기에는 반드시 전달할 번호가 필요하다.)
  }
  
  
  @RequestMapping(value="/contact/delete.do", method=RequestMethod.POST)
  public String delete(@RequestParam(value="contact_no", required=false, defaultValue="0") int contact_no, RedirectAttributes redirectAttributes) {
    log.info("delete:" + contact_no);
    int deleteResult = contactService.deleteContact(contact_no);
    redirectAttributes.addFlashAttribute("deleteResult", deleteResult);
    return "redirect:/contact/list.do";
  }
  
}