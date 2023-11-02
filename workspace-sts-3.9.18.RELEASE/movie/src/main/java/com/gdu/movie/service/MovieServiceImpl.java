package com.gdu.movie.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gdu.movie.dao.MovieMapper;
import com.gdu.movie.dto.MovieDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

  private final MovieMapper movieMapper;
  
  
  @Override
  public Map<String, Object> getMovieList() {

    int movieCount = movieMapper.getMovieCount();
    List<MovieDto> list = movieMapper.getMovieList();
    
    return Map.of("message", "전체 " + movieCount + "개의 목록을 가져왔습니다. "
                , "list", list
                , "status", 200);   // 전체 목록은 성공과 실패값이 없어서 status(상태값)을 200으로 고정시켜 놓은 것. (status값은 검색 할 때만 200이냐, 500이냐고 바꾸면 된다.)
      
  }
  
}
