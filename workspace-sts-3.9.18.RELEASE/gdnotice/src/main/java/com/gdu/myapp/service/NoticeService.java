package com.gdu.myapp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gdu.myapp.dto.NoticeDto;

public interface NoticeService {
  public List<NoticeDto> getNoticeList();
  public NoticeDto getNotice(HttpServletRequest request);
  public int addNotice(NoticeDto noticeDto);
  public int removeNotice(HttpServletRequest request);
}
