package com.gdu.app06.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gdu.app06.dao.BoardDao;
import com.gdu.app06.dto.BoardDto;
import com.gdu.app06.service.BoardServiceImpl;
import com.gdu.app06.service.IBoardService;

@Configuration  // 클래스 레벨의 Configuration 을 붙인다.
public class BoardConfig {
  
  @Bean                           // <bean class="BoardDto" id="boardDto1" />
  public BoardDto boardDto1() {   // boardDto1 이 Bean 의 이름이다. (메소드의 이름이 Bean의 이름이다.)
    return new BoardDto(1, "제목1", "작성자1");   // return 타입이 Bean의 클래스 타입이다.
  }
  
  @Bean                           // <bean class="BoardDto" id="boardDto1" />
  public BoardDto boardDto2() {   // boardDto1 이 Bean 의 이름이다. (메소드의 이름이 Bean의 이름이다.)
    return new BoardDto(2, "제목2", "작성자2");   // return 타입이 Bean의 클래스 타입이다.
  }
  
  @Bean                           // <bean class="BoardDto" id="boardDto1" />
  public BoardDto boardDto3() {   // boardDto1 이 Bean 의 이름이다. (메소드의 이름이 Bean의 이름이다.)
    return new BoardDto(3, "제목3", "작성자3");   // return 타입이 Bean의 클래스 타입이다.
  }
  
  // ↑ 스프링 컨테이너에 Bean 객체 3개가 저장됐다.
  
  @Bean
  public BoardDao boardDao() {
    return new BoardDao();
  }
  
  @Bean
  public IBoardService iBoardService() {
    return new BoardServiceImpl();
  }
  
}
