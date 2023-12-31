package com.gdu.app13.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdu.app13.peopleDto.PeopleDto;
import com.gdu.app13.service.PeopleService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PeopleController {

  private final PeopleService peopleService;
  
  @RequestMapping(value="/list.do", method=RequestMethod.GET)
  public List<PeopleDto> list() {
    return peopleService.getDtoList();
  }
  
}