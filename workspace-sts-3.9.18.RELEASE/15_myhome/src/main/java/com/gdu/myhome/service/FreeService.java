package com.gdu.myhome.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface FreeService {
  public int addFree(HttpServletRequest request); // service가 model 받아가는 건 거의 없다.
  public void loadFreeList(HttpServletRequest request, Model model);
  public int addReply(HttpServletRequest request);
  public int removeFree(int freeNo);  // 삭제할 때 번호만 넘기면 되니까 request써서 꺼내서 전달하면 번거로우니 'int free로 번호만 전달한다. HttpServletrequest로도 가능. 
}