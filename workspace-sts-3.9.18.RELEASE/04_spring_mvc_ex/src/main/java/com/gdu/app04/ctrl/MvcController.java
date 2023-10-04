package com.gdu.app04.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
  public String register(HttpServletRequest request, Model model) {
    int articleNo = Integer.parseInt(request.getParameter("articleNo"));
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    System.out.println(articleNo + "," + title + "," + content);
    model.addAttribute("articleNo", articleNo);
    model.addAttribute("title", title);
    model.addAttribute("content", content); // 목록을 전달할 때는 model 에 list 를 담아서 쓰면 됨.
    return "/WEB-INF/article/result.jsp";
  }
  
  // 두 번째 파라미터 끌어오도록 요청하는 방법 = RequestParam 을 이용한 방법.
  // @RequestMapping(value="/register.do", method=RequestMethod.POST)
  public String register2(@RequestParam(value="articleNo") int articleNo
                        , @RequestParam(value="title") String title
                        , @RequestParam(value="content") String content
                        , Model model) {
    ArticleVo vo = new ArticleVo(articleNo, title, content);
    model.addAttribute("vo", vo); //위에서 ArticleVo를 vo로 생성자 만들어주고 밑에 model에 담아 준다.
    return "/WEB-INF/article/result.jsp";
    }
  
  //@RequestMapping 의 좋은 점은 생략도 가능하다. ( @RequestParam(value="articleNo") 이 부분은 사실 없어도 됨.
  
  // 세 번째 파라미터 끌어오도록 요청하는 방법 : 커맨드 객체를 이용해서 파라미터를 받는 방법 (파라미터와 이름이 같은 필드를 가지고 있는 객체:커맨드 객체. -> ArticleVo 자바파일이다. = 이것으로 파라미터를 요청한다.
  // @RequestMapping(value="/register.do", method=RequestMethod.POST)
  public String register3(ArticleVo vo) { // 이미 model에 ArticleVo 는 1번, vo는 2번으로 자동 저장이 되어진다. (ArticleVo 라는 타입이름으로 저장이 된다.)
    return "/WEB-INF/article/result.jsp";
    // 커맨드 객체는 model에 저장하는 코드가 불필요하다. (이미 위 구문 자체로 커맨드 객체는 model에 저장이 됩니다.)
    // 파라미터들을 ArticleVo 에 다 집어넣어 준다.
  }
  
  // @ModelAttribute 에 저장할 때 ArticleVo 말고 vo로 이름만 바꿔서 저장하라는 것(이름만 바꾸는 기능)
  @RequestMapping(value="/register.do", method=RequestMethod.POST)
  public String register4(@ModelAttribute(value="atcVo") ArticleVo vo) {
    return "/WEB-INF/article/result.jsp";
  }
  
  
  
  
  
  
  
  
  
  
}
