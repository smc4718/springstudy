package com.gdu.app09;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdu.app09.dao.ContactDao;
import com.gdu.app09.dto.ContactDto;

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
// JUnit4를 이용한다.
@RunWith(SpringJUnit4ClassRunner.class)

// ContactDao Bean 생성 방법을 알려준다.
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")

// 테스트 메소드의 이름 오름차순(알파벳순)으로 테스트를 수행한다.
@FixMethodOrder(MethodSorters.NAME_ASCENDING)     // @FixMethodOrder(MethodSorters.NAME_ASCENDING) : 메소드 순서 고정(메소드이름순으로 오름차순)
public class ContactUnitTest {

  @Autowired  // Spring Container ContactDao 객체(Bean)를 가져온다.
  private ContactDao contactDao;
  
  @Test   // 테스트를 수행한다.
  public void test01_삽입테스트() {
    ContactDto contactDto = new ContactDto(0, "이름", "연락처", "이메일", "주소", "");
    int insertResult = contactDao.insert(contactDto);
    assertEquals(1, insertResult);   // assertEquals(기대한 값, 실제나온값) : 기대값과 실제 나온 값이 같으면 테스트 성공이다.
  }

  @Test // 테스트를 수행한다.
  public void test02_조회테스트() {
    int contact_no = 1;
    ContactDto contactDto = contactDao.selectContactByNo(contact_no);
    assertNotNull(contactDto);  // contactDto가 not null이면 테스트 성공이다.
  }
  
  @Test // 테스트를 수행한다.
  public void test03_삭제테스트() {
    int contact_no = 1;
    int deleteResult = contactDao.delete(contact_no);
    assertEquals(1, deleteResult);  // insertResult가 1이면 테스트 성공이다.
  //  assertNull(contactDao.selectContactByNo(contact_no));  = select 결과가 null이면 테스트 성공.
  }
}
