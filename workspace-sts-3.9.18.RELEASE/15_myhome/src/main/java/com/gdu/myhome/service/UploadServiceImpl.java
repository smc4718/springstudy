package com.gdu.myhome.service;

import org.springframework.stereotype.Service;

import com.gdu.myhome.dao.UploadMapper;
import com.gdu.myhome.util.MyFileUtils;
import com.gdu.myhome.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

  private final UploadMapper uploadMapper;  // 매퍼 이용할 용도
  private final MyFileUtils myFileUtils;    // 파일첨부할 용도
  private final MyPageUtils myPageUtils;    // 목록 다룰 용도
  
}
