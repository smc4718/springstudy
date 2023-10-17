package com.gdu.app.service;

import java.util.List;

import com.gdu.app.dto.NoticeDto;

public interface NoticeService {
  // getNoticeList 라는 이름을 그대로 써도 되고, 아무렇게나 써도 된다.
  // 서비스 1개 , 매퍼 1개 끼리 1:1매칭 시킨다면 같은 이름을 쓰고, 아니라면 다르게 쓴다.
  // 사용자에게 친화적인 단어는 Service로 만들고, DB에 친화적이면 Dao에 만든다.
  // Mapper가 넘겨 주는 여러 개의 Service를 받을 List를 만들어 준다,
  public int modifyNotice(NoticeDto noticeDto);
  public NoticeDto getNotice(int noticeNo);  // 받아서 넘기고만 해주고 있다.
  public int addNotice(NoticeDto noticeDto); // 하나만 전달한다.
  public List<NoticeDto> getNoticeList();
}
