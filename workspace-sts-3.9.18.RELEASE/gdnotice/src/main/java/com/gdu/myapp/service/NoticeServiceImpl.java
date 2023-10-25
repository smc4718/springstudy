package com.gdu.myapp.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gdu.myapp.dao.NoticeDao;
import com.gdu.myapp.dto.NoticeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {

  private final NoticeDao noticeDao;
  
  @Override
  public List<NoticeDto> getNoticeList() {
    return noticeDao.getNoticeList();
  }

  @Override
  public NoticeDto getNotice(HttpServletRequest request) {
    Optional<String> opt = Optional.ofNullable(request.getParameter("no"));
    int no = Integer.parseInt(opt.orElse("0"));
    return noticeDao.getNotice(no);
  }

  @Override
  public int addNotice(NoticeDto noticeDto) {
    int addResult = noticeDao.addNotice(noticeDto);
    return addResult;
  }


  @Override
  public int removeNotice(HttpServletRequest request) {
    Optional<String> opt = Optional.ofNullable(request.getParameter("no"));
    int no = Integer.parseInt(opt.orElse("0"));
    return noticeDao.removeNotice(no);
  }

}
