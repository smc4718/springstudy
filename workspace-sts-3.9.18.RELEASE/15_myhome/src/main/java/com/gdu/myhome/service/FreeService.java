package com.gdu.myhome.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface FreeService {
  public int addFree(HttpServletRequest request);  // service가 model 받아가는 건 거의 없다.
}
