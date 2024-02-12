package com.gdu.myhome.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gdu.myhome.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class InactiveUserBatch {

  private final UserService userService;
  
  @Scheduled(cron="0 0 0 1/1 * ?")  // 언제 동작해라(크론식) = 매일 자정에 동작
  public void execute() {
    userService.inactiveUserBatch();
  }
  
  
}