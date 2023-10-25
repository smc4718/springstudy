package com.gdu.myhome.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
    
    @Override
    public void loadFreeList(HttpServletRequest request, Model model) {
    
      Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
      int page = Integer.parseInt(opt.orElse("1")); // 페이지 정보.
      
      int display = 10; // 한 화면에 몇 개씩 보여줄 것인가.
      
      int total = freeMapper.getFreeCount();  // 갯수 구하기
      
      myPageUtils.setPaging(page, total, display); // myPageUtils 를 계산. (myPageUtils 를 계산해야 userMapper.xml에 begin 하고 end 가 계산된다.)
      
      // begin 하고 end 구하기.
      Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                     , "end", myPageUtils.getEnd());
      
      // 목록을 가져오자.
      List<FreeDto> freeList = freeMapper.getFreeList(map);
      
      // 가져온 목록을 model에 저장시키기
      model.addAttribute("freeList", freeList); // 목록 저장시키기.
      model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/free/list.do")); // 페이징 정보 저장시키기. (1 2 3 4 5 6 ... 목록을 스트링으로 만들어주는 게 paging 이 하는 일)
                                                        //     ↑ 이 url 은 페이지가 넘어가게 해준다.(ex:  /free/list.do?page=3 ) 
                                                       // 목록에서 목록을 넘겨주는 주소를 넘겨준다.
      model.addAttribute("beginNo", total - (page - 1) * display); // 페이지 시작값 계산
    }  //이제 컨트롤러로 가서 호출해 준다.
}
