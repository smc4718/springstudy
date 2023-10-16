package com.gdu.app14.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.app14.dto.MemberDto;
import com.gdu.app14.service.MemberService;

import lombok.RequiredArgsConstructor;


/*
 * REST(REpresentational State Transfer)
 * 1. 요청 주소를 작성하는 새로운 방식이다.
 * 2. 변수를 파라미터로 추가하는 Query String 방식이 아니다.
 * 3. 변수를 주소에 포함시키는 Path Variable 방식을 사용한다.
 * 4. "요청 주소(URL) + 요청 방식(Method)"을 합쳐서 요청을 구분한다.
 * 5. 예시
 *          URL         Method
 *  1) 목록 /members    GET      실제전송시 : /members/page/1 와 같이 나타남.("페이지가 1" 이라는 의미, '1' 자리에는 "{page}" 변수가 들어간다.)    
 *  2) 상세 /members/1  GET      : members 중 memberNo가 1인 회원을 가져오시오.
 *  3) 삽입 /members    POST     : 1)번과 같으나 Method 가 달라지니 동작이 달라진다.
 *  4) 수정 /members    PUT
 *  5) 삭제 /members/1  DELETE   : 누구를 삭제할지 알아야하니까 members 이다.(members중 memberNo가 1인 회원을 삭제하시오.)
 */




@RequiredArgsConstructor
@RestController  // 모든 메소드에 @ResponseBody를 추가한다.
public class MemberController {
  
  private final MemberService memberService;
  
//회원 등록 요청
 @RequestMapping(value="/members", method=RequestMethod.POST, produces="application/json") // produces 가 반환타입이 무엇이냐고 묻는 것이다 (Map<String, Object> 을 반환하는 것이다.)
 public Map<String, Object> registerMember(@RequestBody MemberDto memberDto, HttpServletResponse response) { // Response가 필요한 이유는 Service가 Response필요로 하기 때문이다.
                                                                 // Service에서 Response하려면 컨트롤러가 Response를 선언해야 합니다.
   return memberService.register(memberDto, response);
 }
  
  // 회원 목록 요청
  // 경로에 포함되어 있는 변수 {page}는 @PathVariable(경로변수)을 이용해서 가져올 수 있다.
  @RequestMapping(value="/members/page/{p}", method=RequestMethod.GET, produces="application/json")
  public Map<String, Object> getMembers(@PathVariable(value="p", required=false) Optional<String> opt) { // value의 이름은 매핑밸류의 {p} 와 동일한 이름으로 작성해서 맞춰준다.
    int page = Integer.parseInt(opt.orElse("1")); // 페이지 저장이 안되면 1을 꺼내라.
    return memberService.getMembers(page);  // Service에 page 를 전달해줘야 한다.
  }
  
}
