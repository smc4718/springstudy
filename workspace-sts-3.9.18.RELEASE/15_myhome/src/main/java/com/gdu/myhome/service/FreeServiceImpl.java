package com.gdu.myhome.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.gdu.myhome.dao.FreeMapper;
import com.gdu.myhome.dto.FreeDto;
import com.gdu.myhome.util.MyPageUtils;
import com.gdu.myhome.util.MySecurityUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FreeServiceImpl implements FreeService {

    // service가 사용해야 하는 것(Mapper, MyPageUtils)
    private final FreeMapper freeMapper;
    private final MyPageUtils myPageUtils;
    private final MySecurityUtils mySecurityUtils;

    @Override
    public int addFree(HttpServletRequest request) {
      
      // 작성자 이메일과 콘텐츠 받아오기
      String email = request.getParameter("email");
      String contents = mySecurityUtils.preventXSS(request.getParameter("contents"));
      
      // free에 받아온 내용 담기.
      FreeDto free = FreeDto.builder()
                      .email(email)
                      .contents(contents)
                      .build();
      
      // 담은 내용 전달
      return freeMapper.insertFree(free);
    }
    
}
