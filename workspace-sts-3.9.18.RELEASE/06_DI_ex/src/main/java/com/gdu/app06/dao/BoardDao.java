package com.gdu.app06.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.gdu.app06.dto.BoardDto;

// Dao 에 @Repository 를 만들지 않는 대신 @Bean 을 쓴 것.
public class BoardDao {
  
  // 현재 DB 가 없어서 임시로 DB를 만들어서 씀 ↓

  
  private  BoardDto boardDto1;
  private  BoardDto boardDto2;
  private  BoardDto boardDto3;
  
  // 왠만해서는 필드에 @Autowired 붙이지 말자.
  // @Autowired 는 타입이 같으면 매개변수로 들어옵니다.(같은 타입이 둘 이상있으면 이름으로 비교한다.)
  @Autowired      // ↓ 매개변수로 boardDto 객체 3개가 들어온다.
  public void setBoardDao(BoardDto boardDto1, BoardDto boardDto2, BoardDto boardDto3) {

    this.boardDto1 = boardDto1;
    this.boardDto2 = boardDto2;
    this.boardDto3 = boardDto3;
  }

  public List<BoardDto> getBoardList() {
    return Arrays.asList(boardDto1, boardDto2, boardDto3);
  }
  
  public BoardDto getBoardByNo(int boardNo) {
    BoardDto boardDto = null;
    if(boardDto1.getBoardNo() == boardNo) {
      boardDto = boardDto1;
    } else if(boardDto2.getBoardNo() == boardNo) {
      boardDto = boardDto2;
    } else if(boardDto3.getBoardNo() == boardNo) {
      boardDto = boardDto3;
    }
    return boardDto;
  }
  
}
