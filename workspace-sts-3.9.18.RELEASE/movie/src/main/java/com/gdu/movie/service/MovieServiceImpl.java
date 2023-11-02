package com.gdu.movie.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.gdu.movie.dao.MovieMapper;
import com.gdu.movie.dto.MovieDto;
import com.gdu.movie.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

  private final MovieMapper movieMapper;
  private final MyPageUtils myPageUtils;
  
  
  @Override
  public Map<String, Object> getMovieList() {

    int movieCount = movieMapper.getMovieCount();
    List<MovieDto> list = movieMapper.getMovieList();
    
    return Map.of("message", "전체 " + movieCount + "개의 목록을 가져왔습니다. "
                , "list", list
                , "status", 200);   // 전체 목록은 성공과 실패값이 없어서 status(상태값)을 200으로 고정시켜 놓은 것. (status값은 검색 할 때만 200이냐, 500이냐고 바꾸면 된다.)
      
  }
  
  
  // 검색 결과 목록
  @Transactional(readOnly=true)
  @Override
  public void MovieSearchList(HttpServletRequest request, Model model) {
    
    String column = request.getParameter("column");
    String query = request.getParameter("query");
    
    Map<String, Object> map = new HashMap<>();
    map.put("column", column);
    map.put(query, query);
    
    int total = movieMapper.getSearchCount(map);
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
    String strPage = opt.orElse("1");
    int page = Integer.parseInt(strPage);
   
    int display = 10; 
   
    myPageUtils.setPaging(page, total, display);  
    
    map.putIfAbsent("begin", myPageUtils.getBegin()); 
    map.put("end", myPageUtils.getEnd()); 
    
    List<MovieDto> searchList = movieMapper.getSearchList(map);
    
    model.addAttribute("searchList", searchList);                      
    model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/search.do", "column=" + column + "&query=" + query));
    model.addAttribute("beginNo", total - (page - 1) * display);
        
  }
  
}
