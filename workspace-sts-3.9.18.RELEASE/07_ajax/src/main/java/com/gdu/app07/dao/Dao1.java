package com.gdu.app07.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdu.app07.dto.Dto1;

@Repository
public class Dao1 {

  private Dto1 a;
  private Dto1 b;
  private Dto1 c;
  
  @Autowired
  public void setBean(Dto1 a, Dto1 b, Dto1 c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  public List<Dto1> getDtoList() {
    return Arrays.asList(a, b, c);
  }
  
  public Dto1 getDto(int no) {
    Dto1 dto = null;
    if(no == 1) {
      dto = a;
    } else if(no == 2) {
      dto = b;
    } else if(no == 3) {
      dto = c;
    }
    return dto;
  }
  
}
