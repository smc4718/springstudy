package com.gdu.app04.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app04.vo.ArticleVo;

@Controller
public class MvcController {

  // 파라미터를 끌어오도록 요청하는 세 가지 방법 ( 셋 중에 하나만 할 줄 알면 됨 )
  
  // DispatcherServlet(servlet-context.xml)에서 ViewResolver를 제거했으므로 JSP의 전체 경로를 모두 작성해야 한다.
  
  @RequestMapping(value="/", method=RequestMethod.GET)  // '/' 로 적어주면 ContextPath를 의미한다. GET 방식으로 ContextPath를 요청한다.
  public String main() {
    return "/WEB-INF/main.jsp";
  }
  
  @RequestMapping(value="/write.do", method=RequestMethod.GET)
  public String write() {
    return "/WEB-INF/article/write.jsp";  // viewresolver 가 없기 때문에 경로는 항상 전체경로로 잡아준다.
// write.do 가 인식이 되면서, 그 경로로 이동하게 된다.
  }
 
  // 첫 번째 파라미터 끌어오도록 요청하는 방법 = HttpServletRequest 를 이용한 방법
  // @RequestMapping(value="/register.do", method=RequestMethod.POST)
  public String register(HttpServletRequest request) {
    int articleNo = Integer.parseInt(request.getParameter("articleNo"));
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    System.out.println(articleNo + "," + title + "," + content);
    return "/WEB-INF/article/write.jsp";
  }
  
  // 두 번째 파라미터 끌어오도록 요청하는 방법 = RequestParam 을 이용한 방법.
  // @RequestMapping(value="/register.do", method=RequestMethod.POST)
  public String register2(@RequestParam(value="articleNo") int articleNo
                        , @RequestParam(value="title") String title
                        , @RequestParam(value="content") String content) {
    System.out.println(articleNo + "," + title + "," + content);
    return "/WEB-INF/article/write.jsp";
    }
  
  //@RequestMapping 의 좋은 점은 생략도 가능하다. ( @RequestParam(value="articleNo") 이 부분은 사실 없어도 됨.
  
  // 세 번째 파라미터 끌어오도록 요청하는 방법 : 커맨드 객체를 이용해서 파라미터를 받는 방법 (파라미터와 이름이 같은 필드를 가지고 있는 객체:커맨드 객체. -> ArticleVo 자바파일이다. = 이것으로 파라미터를 요청한다.
  @RequestMapping(value="/register.do", method=RequestMethod.POST)
  public String register3(ArticleVo vo) {
    System.out.println(vo);
    return "/WEB-INF/article/write.jsp";
    // 파라미터들을 ArticleVo 에 다 집어넣어 준다.
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}
