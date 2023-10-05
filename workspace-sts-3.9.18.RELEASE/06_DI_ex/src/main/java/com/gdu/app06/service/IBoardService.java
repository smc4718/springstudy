package com.gdu.app06.service;

import java.util.List;

import com.gdu.app06.dto.BoardDto;

// Service 에 @Service 를 만들지 않는 대신 @Bean 을 쓴 것.
public interface IBoardService {
  public List<BoardDto> getBoardList();
  public BoardDto getBoardByNo(int boardNo);
}
