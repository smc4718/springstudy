package com.gdu.app05.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdu.app05.service.BoardService;
import com.gdu.app05.service.BoardServiceImpl;

@Controller // 컨트롤러 전용 @Component 이다.
public class BoardController {
  
  /*
   * DI
   * 1. Dependency Injection
   * 2. Spring Container에 저장된 객체를 가져오는 방식이다.
   * 3. 주요 Annotation
   *  1) @Inject    (우리가 쓸 일 없음)
   *    (1) javax.inject.Inject
   *    (2) 타입이 일치하는 객체를 찾아서 가져온다. 없으면 오류가 발생한다.
   *    (3) 동일한 타입의 객체가 2개 이상 있다면 이름이 일치하는 객체를 가져온다.
   *  2) @Resource  (우리가 쓸 일 없음)
   *    (1) javax.annotation.Resource
   *    (2) javax-annotation-api dependency를 pom.xml에 추가해야 사용할 수 있다.
   *    
   *    https://mvnrepository.com/artifact/javax.annotation/javax.annotation-api/1.3.2
   *    <dependency>
          <groupId>javax.annotation</groupId>
          <artifactId>javax.annotation-api</artifactId>
          <version>1.3.2</version>
        </dependency>
        (3) 이름이 일치하는 객체를 찾아서 가져온다. 없으면 오류가 발생한다.
      3) @Autowired
        (1) org.springframework.beans.factory.annotation.Autowired
        (2) @Inject 기반의 Spring Annotation이다. (타입 기반)
        (3) 객체 이름을 기준으로 가져올 수 있도록 @Qualifier(org.springframework.beans.factory.annotation.Qualifier)를 사용할 수 있다.
   */
  
  /*
   * BoardService DI 처리 방법
   * 
   * 1. BoardService 타입의 BoardServiceImpl 객체를 Spring Container에 넣는다. (아래 3가지 방법 중 하나 이용)
   *  1) <bean> 태그            : /WEB-INF/spring/root-context.xml
   *  2) @Configuration + @Bean : com.gdu.app05.config.AppConfig.java
   *  3) @Component
   *  
   * 2. @Autowired 를 이용해서 Spring Container에서 BoardService 타입의 객체를 가져온다.
   *  1) 필드에 주입하기
   *   : @Autowired
   *   : private BoardService boardService;   <= 필드.
   *   
   *  2) 생성자에 주입하기 (생성자의 매개변수로 주입된다 = BoardService boardService 로 주입됨.)
   *   : @Autowired
   *   : public BoardController(BoardService boardService) {
   *      super();
   *      this.boardService = boardService;
   *     }
   *      
   *  3) Setter 형식의 메소드에 주입하기 (매개변수로 주입된다 = BoardService boardService 로 주입됨.), 메소드이름이 set이 아니어도 됨.
   *   : @Autowired
   *   : public void setBoardService(BoardService boardService) {
   *       this.boardService = boardService;
   *     }
   *    
   */
     
  
  
  //@Autowired 의 역할 : 타입이 일치하는 bean 을 알아서 가져온다. (타입이 틀려도 Inject 기반이기 때문에 타입이 틀리면 자동으로 이름을 찾아서 가져온다.)
  
  // 주입된 boardService 객체의 변경 방지를 위한 final 처리
  private final BoardService boardService;
  
  // BoardService에 final 처리를 하면 생성자 주입만 가능하다.(필드 주입과 Setter 주입은 불가능하다.)
  // 생성자 주입의 @Autowired는 생략할 수 있으므로 @RequiredArgsConstructor와 같은 Annotation으로 대체할 수 있다.
  @Autowired  // (생성자에 주입)
  public BoardController(BoardService boardService) {
    super();
    this.boardService = boardService;
  }



  @RequestMapping(value="/board/list.do", method=RequestMethod.GET)
  public String list(Model model) {
    model.addAttribute("boardList", boardService.getBoardList());
    return "board/list";
  }
  
}
