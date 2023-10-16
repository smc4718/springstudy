package com.gdu.app14.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.app14.dao.MemberMapper;
import com.gdu.app14.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service  // 서비스패키지는 서비스 애노테이션을 붙여주면 Bean 객체를 만들어 준다.
public class MemberServiceImpl implements MemberService {

  @Autowired
  private final MemberMapper memberMapper;
  
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
  
}
