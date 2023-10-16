package com.gdu.app14.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.gdu.app14.dto.MemberDto;

public interface MemberService {
  
  // 회원 등록
  public Map<String, Object> register(MemberDto memberDto, HttpServletResponse response);
  
  // 회원 목록                          // 페이징처리할 때 가장 중요한 파라미터는 몇페이지인지 알 수 있는 '페이지정보' 파라미터가 필요하다.
  public Map<String, Object> getMembers(int page);   // getMembers() 는 '페이지정보' 파라미터인 int page 를 받아올 수 있도록 적어준다.
           
}
