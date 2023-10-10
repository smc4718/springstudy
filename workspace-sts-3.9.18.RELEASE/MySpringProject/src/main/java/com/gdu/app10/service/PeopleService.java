package com.gdu.app10.service;

import java.util.List;

import com.gdu.app10.peopleDto.PeopleDto;

public interface PeopleService {
  // 여기서 getDto와 getDtoList 를 만들어준다.
    public List<PeopleDto> getDtoList();
    public PeopleDto getDto(String name);
  
}
