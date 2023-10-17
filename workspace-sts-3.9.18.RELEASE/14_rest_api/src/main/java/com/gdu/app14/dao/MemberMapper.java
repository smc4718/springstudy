package com.gdu.app14.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app14.dto.MemberDto;

@Mapper
public interface MemberMapper {

  // 삽입
  public int insertMember(MemberDto memberDto); // 인터페이스니까 본문이 없다.
  
  // 목록
  public List<MemberDto> getMemberList(Map<String, Object> map);
  
  // 전체 개수
  public int getMemberCount();
  
  // 회원 조회
  public MemberDto getMember(int memberNo);  // 메소드의 이름들은 memberMapper.xml의 id 와 같게 작성 한다. 

  // 회원 정보 수정
  public int updateMember(MemberDto memberDto);
  
  
  
  
  
}
