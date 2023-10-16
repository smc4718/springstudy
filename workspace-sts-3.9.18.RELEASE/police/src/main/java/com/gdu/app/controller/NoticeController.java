package com.gdu.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app.dto.NoticeDto;
import com.gdu.app.service.NoticeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NoticeController {

  private final NoticeService noticeService;  // 컨트롤러는 서비스를 불러서 가져다가 쓴다.

  @RequestMapping(value="/notice/list.do", method=RequestMethod.GET) // URL주소에 어떻게 연결해줄지 적어준다.(요청주소가 localhost:8080/app/list.do 로 나온다.)
  public String list(Model model) {  // forward할 데이터는 Model에 저장한다.
    List<NoticeDto> noticeList = noticeService.getNoticeList(); // 서비스를 불러서 결과를 저장시킨다. 
    model.addAttribute("noticeList", noticeList);  // forward 할 데이터 저장하기(저장한 이름은 noticeList)                            
   return "notice/list"; // notice 폴더 아래의 list.jsp로 forward 하시오.
  // RequestMapping의 "/notice/list.do" 와 return "notice/list"; 는 관계가 없다.
  }                   //  ↑ URL 매핑주소.            ↑ notice 폴더 안에 list.jsp 파일        
  
  
  @RequestMapping(value="/notice/write.do", method=RequestMethod.GET)
  public String write() {
    return "notice/write";
  }
  
  // 삽입(Insert)은 POST 메소드로 사용해야 한다.
  @RequestMapping(value="/notice/save.do", method=RequestMethod.POST)           //저장하는 방법 3가지 : 리퀘스트로 받기(이건 잘 안씀), 하나로 모아서 받기(이걸 추천), 변수 3개 선언해서 받기.
  public String save(NoticeDto noticeDto, RedirectAttributes redirectAttributes) {// redirect 데이터는 RedirectAttributes에 저장한다.
                  // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 모으라면 모아주는 객체를 "Command 객체" 라고 한다(이것 -> NoticeDto noticeDto)
    int addResult = noticeService.addNotice(noticeDto); // 작성되면 위 write메소드에서 return "notice/write"; 으로 받아서 온다.
    redirectAttributes.addFlashAttribute("addResult", addResult);
    return "redirect:/notice/list.do";  // 위에서 저장하고 (list.do)목록보기로 이동한다. 위에 list 메소드로 다시 올라간다.
    // redirect 할 때도 데이터를 보낼 수 있다.
  }
  

  
}
