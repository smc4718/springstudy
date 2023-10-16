package com.gdu.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.app.dao.NoticeMapper;
import com.gdu.app.dto.NoticeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service  // 애노테이션을 붙여서 NoticeServiceImpl을 빈으로 생성해준다.
public class NoticeServiceImpl implements NoticeService {
  
  private final NoticeMapper noticeMapper;
  
  // ServiceImpl은 돌아온 값을 바로 옆으로 넘겨주는 역할인데, 왜있는지 이해가 안될 정도로 그냥 넘겨주기만 한다.(이렇게 이해하면 됨)
  
  @Override           // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Controller한테 받아오는 것(noticeDto).
  public int addNotice(NoticeDto noticeDto) {
    return noticeMapper.addNotice(noticeDto); // addNotice(NoticeDto noticeDto) 에서 받아온 noticeDto 를 그대로 전달해 준다.
  }

  @Override           // ↓ 반대로 받아온 것도, 전달하는 것도 없는 경우
  public List<NoticeDto> getNoticeList() {
    return noticeMapper.getNoticeList();  // Service에서 받아온 목록을 그대로 되돌려 준다.
  }
  
}
