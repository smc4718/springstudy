package com.gdu.app13.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdu.app13.peopleDao.PeopleDao;
import com.gdu.app13.peopleDto.PeopleDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PeopleServiceImpl implements PeopleService {

  private final PeopleDao peopleDao;

  @Override
  public List<PeopleDto> getDtoList() {
    return peopleDao.getDtoList(); // 해결 : 타입이 아닌 객체를 만들었으니 객체 이름을 적어줌 ( PeopleDto 가 아닌 peopleDto로 씀 )
  }
  
  @Override
  public PeopleDto getDto(String name) {
    int no = 0;
    if(name.equals("유재석")) {
      no = 1;
    } else if(name.equals("자연인")) {
      no = 2;
    }   // ' } ' 닫는 중괄호를 안 써서 에러가 났었음.
    return peopleDao.getDto(no);
  }
  
 }


