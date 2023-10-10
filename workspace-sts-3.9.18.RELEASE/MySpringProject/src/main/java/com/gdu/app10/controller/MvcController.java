package com.gdu.app10.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class MvcController {

  @RequestMapping(value="/", method=RequestMethod.GET)
  public String index() {
    return "index";
  }
    
  @RequestMapping(value="/ajax1.do", method=RequestMethod.GET)
  public String ajax1() {
    return "ajax1";
  }
}