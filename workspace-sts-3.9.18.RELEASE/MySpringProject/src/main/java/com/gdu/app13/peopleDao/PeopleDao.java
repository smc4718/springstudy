package com.gdu.app13.peopleDao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdu.app13.peopleDto.PeopleDto;

@Repository
public class PeopleDao {

  private PeopleDto pd1;
  private PeopleDto pd2;
  private PeopleDto pd3;
  
  @Autowired
  public void SetBean(PeopleDto pd1, PeopleDto pd2, PeopleDto pd3) {
    this.pd1 = pd1;
    this.pd2 = pd2;
    this.pd3 = pd3;
  }
  
  public List<PeopleDto> getDtoList() {
    return Arrays.asList(pd1, pd2, pd3);
  }
  
  public PeopleDto getDto(int no) {
    PeopleDto dto = null;
    if(no == 1) {
      dto = pd1;
    } else if(no == 2) {
      dto = pd2;
    } else if(no == 3) {
      dto = pd3;
    }
    return dto;
  }
  

}
