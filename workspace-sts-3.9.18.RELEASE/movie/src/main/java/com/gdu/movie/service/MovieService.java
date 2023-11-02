package com.gdu.movie.service;

import java.util.Map;

public interface MovieService {
  public Map<String, Object> getMovieList();   // Ajax은 Map으로 반환.
}
