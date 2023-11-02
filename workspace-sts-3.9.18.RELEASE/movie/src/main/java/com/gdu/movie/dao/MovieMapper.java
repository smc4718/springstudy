package com.gdu.movie.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.movie.dto.MovieDto;

@Mapper
public interface MovieMapper {
  public int getMovieCount();
  public List<MovieDto> getMovieList();   // getMovieList 니까 List에 resultType="MovieDto" 니까 List<MovieDto>가 된다.
}
