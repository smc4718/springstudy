package com.gdu.app14.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.app14.dao.MemberMapper;
import com.gdu.app14.dto.MemberDto;
import com.gdu.app14.util.PageUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service  // 서비스패키지는 서비스 애노테이션을 붙여주면 Bean 객체를 만들어 준다.
public class MemberServiceImpl implements MemberService {

  @Autowired
  private final MemberMapper memberMapper;
  private final PageUtil pageUtil;   //애초에 PageUtil은 빈이 아니기 때문에 PageUtil.java 에 @Component 붙여줘서 빈으로 만들어 준다(그러면 Autowired가 됨).
  
  @Override
  public Map<String, Object> register(MemberDto memberDto, HttpServletResponse response) {
    
    Map<String, Object> map = null;
    
    try {
      
      int addResult = memberMapper.insertMember(memberDto); // insertMember를 호출하고 호출할 때 회원정보가 담김 memberDto를 불러옴.
      map = Map.of("addResult", addResult); // 그리고 결과를 0 아니면 1로 받아와 그리고 그 결과를 다시 알려줘.
      
    } catch (Exception e) {
      System.out.println(e.getClass().getName()); // 발생한 예외 클래스의 이름 확인
    }
    
    return map;
    
  }
  
  @Override
  public Map<String, Object> getMembers(int page) {
   
    int total = memberMapper.getMemberCount();  // PageUtil의 total로 전달할 값을 불러옴.
    int display = 2;  // PageUtil의 display에 넣어줄 값.
    
    pageUtil.setPaging(page, total, display); // PageUtil로 age, total, display 파라미터 전달.
    
    Map<String, Object> map = Map.of("begin", pageUtil.getBegin()
                                    , "end", pageUtil.getEnd());
    
    // DB로부터 값을 가져와라
    List<MemberDto> memberList = memberMapper.getMemberList(map); // map을 전달.
    String paging = pageUtil.getAjaxPaging();  // pageUtil.getAjaxPaging() 호출만으로 받아올 수 있다.
    
    return Map.of("memberList", memberList
                    , "paging", paging);  // 받아온 값인 memberList 를 반환해준다. 
  }
  
  
}
