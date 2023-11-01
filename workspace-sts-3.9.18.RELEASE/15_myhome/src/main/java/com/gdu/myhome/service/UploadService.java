package com.gdu.myhome.service;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UploadService {
  public int addUpload(MultipartHttpServletRequest request) throws Exception; // 파일첨부때문에 일반HttpServletRequest는 사용 못한다.
}
