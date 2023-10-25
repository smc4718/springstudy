package com.gdu.myapp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdu.myapp.dao.NoticeDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class NoticeUnitTest {

  @Autowired
  private NoticeDao noticeDao;
  
  @Test
  public void test() {
    assertEquals(5, noticeDao.getNoticeList().size());
  }

}
