package com.gdu.movie.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface MovieService {
  public Map<String, Object> getMovieList();   // Ajax은 Map으로 반환.
  public void MovieSearchList(HttpServletRequest request, Model model);
}
