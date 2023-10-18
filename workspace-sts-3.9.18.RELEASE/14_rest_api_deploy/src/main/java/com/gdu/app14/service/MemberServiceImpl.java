package com.gdu.app14.service;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

      int addResult = memberMapper.insertMember(memberDto);
      map = Map.of("addResult", addResult);
    
    } catch(DuplicateKeyException e) {  // UNIQUE 칼럼에 중복 값이 전달된 경우에 발생함
                                        // $.ajax({})의 error 속성으로 응답됨
      try {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        response.setStatus(500);                   // 예외객체 jqXHR의 status 속성으로 확인함
        out.print("이미 사용 중인 아이디입니다."); // 예외객체 jqXHR의 responseText 속성으로 확인함
        out.flush();
        out.close();
        
      } catch(Exception e2) {
        e2.printStackTrace();
      }
      
    } catch(Exception e) {
      System.out.println(e.getClass().getName());  // 발생한 예외 클래스의 이름 확인
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
  
  // 회원 조회
  @Override          
  public Map<String, Object> getMember(int memberNo) {  // Controller로부터 받아온 memberNo를 (int memberNo에 저장)
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("member", memberMapper.getMember(memberNo));  // 저장한 memberNo를 매퍼에 넣어서 반환.
    return map;
  }
  
  //회원 정보 수정
  @Override
  public Map<String, Object> modifyMember(MemberDto memberDto) {
    int modifyResult = memberMapper.updateMember(memberDto);  // Controller 로부터 받아온 수정할 정보.
    
    return Map.of("modifyResult", modifyResult);
  }
  
  
  // 회원 정보 삭제
  @Override
  public Map<String, Object> removeMember(int memberNo) {
    return Map.of("removeResult", memberMapper.deleteMember(memberNo));
  }
  
  // 회원들 정보 삭제
  @Override
  public Map<String, Object> removeMembers(String memberNoList) {
    List<String> list = Arrays.asList(memberNoList.split(","));   //List로 바꿔주는 Arrays.asList(). split의 결과는 String. 삭제할 번호가 담겨져 있는 리스트.
    return Map.of("removeResult", memberMapper.deleteMembers(list));
  }
  
}
