package com.gdu.app13.logback;

import java.text.SimpleDateFormat;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

public class MyLogbackLayout extends LayoutBase<ILoggingEvent> {

  @Override
  public String doLayout(ILoggingEvent event) { // event가 로그찍을 때 자동으로 전달되는 값
    
    StringBuilder sb = new StringBuilder();
    
    // logback.xml 처럼 작성해주기. (event 객체에 기존 들어있는 것으로 끌어다 쓸 수 있음)
    //  [%d{HH:mm:ss, Asia/Seoul}] %-5level:%logger - %msg%n
    sb.append("[");
    sb.append(new SimpleDateFormat("HH:mm:ss").format(event.getTimeStamp()));  // 로그가 찍히는 타임스탬프 값.
    sb.append("]");
    sb.append(String.format("%-5s", event.getLevel()));  // 5자리 문자열을 왼쪽 정렬로 출력하기.
    sb.append(":");
    String loggerName = event.getLoggerName();
    sb.append(loggerName);
    if(loggerName.equals("jdbc.sqlonly")) {
     sb.append("\n"); 
    } else {
    sb.append(" - ");
    }
    sb.append(event.getFormattedMessage());
    sb.append("\n");
    
    return sb.toString();
  }
  
}
