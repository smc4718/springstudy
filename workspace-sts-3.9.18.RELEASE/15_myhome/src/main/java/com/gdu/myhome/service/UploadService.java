package com.gdu.myhome.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UploadService {
  public boolean addUpload(MultipartHttpServletRequest request) throws Exception; // 파일첨부때문에 일반HttpServletRequest는 사용 못한다.
  public Map<String, Object> getUploadList(HttpServletRequest request); // 목록을 ajax으로 넘길 때는 Map을 가장 많이 쓴다. (목록을 안 쓴다면 List도 가능하지만, 확장때문에 Map을 쓴다. Map을 쓰면 목록 외에 다른 것도 담을 수 있기 때문에 확장성에 좋다.)
  // ↑ 컨트롤러로부터 page 번호를 받아온다. (하나만 쓰려면 int page를 써도 되지만, 그 외에도 여러가지를 저장하고 담으려면 HttpServletRequest를 써야 한다.  HttpServletRequest가 여러가지를 담는 확장성이 좋다.)
}
