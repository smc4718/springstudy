package com.gdu.app05.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.gdu.app05.dao.BoardDao;
import com.gdu.app05.dto.BoardDto;

@Service // 서비스 계층(Business Layer) 전용 @Component, Spring Container에 BoardService boardServiceImpl 객체를 생성해 둔다.
public class BoardServiceImpl implements BoardService {
  
  @Autowired
  private BoardDao boardDao;
  

  @Override
  public List<BoardDto> getBoardList() {
    return boardDao.getBoardList();
  }

}