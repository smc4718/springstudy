package com.gdu.app10.aop;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

// @Slf4j : 로그라는 객체를 자동으로 만들어 준다.(이 객체를 통해 log.info 를 만드는 것)
//   └> private static final Logger log = LoggerFactory.getLogger(ContactController.class);
@Slf4j
@Aspect
public class BeforeAop {
  
  // 포인트컷 : 언제 동작하는가?                 // *Controller : 컨트롤러로 끝나는 모든 것.
  @Pointcut("execution(* com.gdu.app10.controller.*Controller.*(..))") // 어떤 메소드에서 동작할지 정해주는 것 (표현식을 전달한다). //  *(..) : 모든 메소드에서 동작한다. (ex: list(..) 리스트 메소드에서만 동작한다.)
  public void setPointCut() { } // 이름만 제공하는 메소드 (동작하지 않음, 이름은 마음대로, 본문도 필요 없다. 여기 붙이는 애너테이션때문에 존재하는 메소드이다.)

  // 어드바이스 : 무슨 동작을 하는가?
  @Before("setPointCut()")  //ContactController.*(..)) = 컨택컨트롤러의 모든 메소드 동작 직전에 수행한다.
  public void beforeAdvice(JoinPoint joinPount) {
    /*
     * Before 어드바이스 (특징)
     * 1. 반환타입 : void
     * 2. 메소드명 : 마음대로
     * 3. 매개변수 : JoinPoint
     */
    
    /* ContactController의 모든 메소드가 동작하기 전에 요청(방식/주소/파라미터) 출력하기 */
    
    // 1. HttpServletRequest 
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = servletRequestAttributes.getRequest();
    
    // 2. 요청 파라미터 -> Map 변환 
    Map<String, String[]> map = request.getParameterMap();
    
    // 3. 요청 파라미터 출력 형태 만들기
    String params = "";
    if(map.isEmpty()) {
      params += "No Parameter";
    } else {
      for(Map.Entry<String, String[]> entry : map.entrySet()) {
        params += entry.getKey() + ":" + Arrays.toString(entry.getValue()) + " "; // 배열을 그냥 출력하면 주소값이 나오니 Arrays.toString()을 써줌.
      }
    }
    
    // 4. 로그 찍기
    log.info("{} {}", request.getMethod(), request.getRequestURI());  // <-  요청방식과 주소를 찍은 것, // request.getMethod() 를 "{}" 에 출력하라는 뜻
    log.info("{}", params);                                           // <-  요청 파라미터
    
  }
  
  
  
}
