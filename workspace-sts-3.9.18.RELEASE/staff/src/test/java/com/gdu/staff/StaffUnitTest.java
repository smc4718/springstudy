package com.gdu.staff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdu.staff.dao.StaffMapper;
import com.gdu.staff.dto.StaffDto;

/*
< JUnit >
자바와 JVM 계열의 언어(예 : 코틀린)에서 사용하는 단위 테스트 프레임워크를 말한다.

단위 테스트(Unit Test)
소스코드의 특정 모듈(프로그램 내 하나의 기능을 부르는 말)이 의도된 대로 정확히 작동하는지 검증하는 절차이며,

함수, 메서드, 개별 코드 같은 작은 단위에 대해 테스트 케이스(Test Case)로 분리하고 테스트 코드를 작성하여 테스트하는 것을 말한다.

외부 API와의 연동이 필수라든가 DB 데이터 접근 등 외부 리소스를 직접 사용해야 하는 테스트라면 단위 테스트가 아니다. 단위 테스트에서 외부와의 연동이 필요하다면 테스트 대역(Test Double)을 사용하면 된다.  

💡 다른 언어도 단위 테스트를 위한 프레임워크가 존재하며 보통 이름을 xUnit이라 칭한다.
예 : CppUnit(C++), NUnit(.NET 프레임워크), unittest(파이썬)

*/

/*
 * JUnit 처리 방법
 * 1. spring-test dependency를 추가한다.
 * 2. @RunWith를 추가한다.
 * 3. @ContextConfiguration을 추가한다.
 *    ContactDao 객체(Bean)를 생성한 방법에 따라서 아래 3가지 방식 중 선택한다.
 *    1) <bean> 태그 : @ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/root-context.xml");
 *    2) @Bean       : @ContextConfiguration(classes=Appconfig.class)
 *   3) @Component   : @ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml");
 */
//JUnit4를 이용한다.
@RunWith(SpringJUnit4ClassRunner.class)

//ContactDao Bean 생성 방법을 알려준다.
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
public class StaffUnitTest {

@Autowired  // Spring Container에서 StaffMapper 객체(Bean)를 가져온다.
private StaffMapper staffMapper;

@Before   // 테스트 메소드 수행 전에 삽입부터 해라.
public void test01_삽입테스트() {
 StaffDto staff = new StaffDto("99999", "김기획", "기획부", 5000);
 int addResult = staffMapper.insertStaff(staff);
 assertEquals(1, addResult);  // addResult가 1이면 테스트 성공이다.
}

@Test  // 테스트를 수행한다.
public void 조회테스트() {
 String sno = "99999";
 StaffDto staff = staffMapper.getStaff(sno);
 assertNotNull(staff);  // null이 아니면 staff를 전달.
} 
  

  
  
  
}