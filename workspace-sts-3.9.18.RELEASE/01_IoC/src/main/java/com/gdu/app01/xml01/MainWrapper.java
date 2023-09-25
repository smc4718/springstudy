package com.gdu.app01.xml01;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainWrapper {
  
  public static void ex01() {
    
 // app-context.xml 파일 읽기 (여기에서 <bean> 태그로 정의해 둔 객체가 생성된다.
    AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml01/app-context.xml");

    // <bean> 태그로 정의된 객체 가져오기
    Calculator calculator = (Calculator)ctx.getBean("calc");  // 다른 방법 : ctx.getBean("calc", Calculator.class) 코드도 동일함.
    
    // 객체 사용해 보기
    calculator.add(1, 2);
    calculator.sub(3, 4);
    calculator.mul(5, 6);
    calculator.div(7, 8);
    
    // app-context.xml 파일 닫기
    ctx.close();
    
  }

  public static void ex02() {
    
    // app-context.xml 파일 읽어서 <bean> 태그에 정의된 객체 만들기 (만들어서 자기 컨테이너에 가지고 오기)
    AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("xml01/app-context.xml");
    
    // 객체 가져오기(man, woman)
    Person man = (Person)ctx.getBean("man");  // id만 전달하고 캐스팅하는 방법
    Person woman = ctx.getBean("woman", Person.class); // 캐스팅안해도 타입을 바꿔주는 방법
 
    // 객체 확인
    System.out.println(man.getName() + ", " + man.getAge());
    man.getCalculator().add(1, 2);  // man 이 가지고 있는 계산기로 더하기 계산.
    System.out.println(woman.getName() + ", " + woman.getAge());
    woman.getCalculator().add(3, 4);
    
    // app-context.xml 파일 닫기
    ctx.close();
    
  }
  
  public static void main(String[] args) {
   ex02();
    
  }

}
