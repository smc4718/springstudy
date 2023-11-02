package com.gdu.movie.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdu.movie.service.MovieService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController     // Ajax 전용 컨트롤러로, Ajax이 아닌 건 들어올 수 없다.
public class MovieController {
  
  private final MovieService movieService;

  @GetMapping(value="/searchAllMovies", produces="application/json")
  public Map<String, Object> searchAllMovies() {
    return movieService.getMovieList();
  }
  
}
