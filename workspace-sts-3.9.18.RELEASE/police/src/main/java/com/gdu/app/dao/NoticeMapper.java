package com.gdu.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app.dto.NoticeDto;

@Mapper
public interface NoticeMapper {
 int modifyNotice(NoticeDto noticeDto);
 //                      ↓ getNotice(int a);  // <- noticeNo 이다. (int a) 이름을 a라고 정하면, noticeMapper.xml의  "WHERE NOTICE_NO = #{noticeNo} 랑 이름을 맞춰준다.
 NoticeDto getNotice(int noticeNo);
 int addNotice(NoticeDto noticeDto); // NoticeMapper.xml 에서 만든 삽입 쿼리문의 id인 addNotice 를 불러온다.
 List<NoticeDto> getNoticeList();  // noticeMapper.xml 의 쿼리문에서 넘어갈 값이 여러 개라서 List 로 받아준다.
                                   // 이렇게 하면 매퍼가 연결됨.
                                   // 게시판 구현이니까 Service가 Mapper를 이용해서 DB를 가는 거지, DB를 안 가서 안 쓸 수도 있다. 1:1 매칭이 아님.
} 

// 추가하는 메소드의 이름들은 memberMapper.xml의 해당하는 id 와 같게 작성 한다. 