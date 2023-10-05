package com.gdu.app07.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.app07.dao.Dao1;
import com.gdu.app07.dto.Dto1;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class Service1Impl implements Service1 {

  private final Dao1 dao1;
  
  @Override
  public List<Dto1> getDtoList() {
    return dao1.getDtoList();
  }

  @Override
  public Dto1 getDto(int no) {
    return dao1.getDto(no);
  }

}
