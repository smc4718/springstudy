package com.gdu.app01.anno01;

import com.gdu.app01.xml01.Calculator;
import com.gdu.app01.xml01.Person;

public class AppConfig {

}

public Calculator calc() {
  return new Calculator();
}

public Person man() {
  Person person = new Person();
  person.setName("뽀로로");
  person.setAge(20);
  person.setCalculator(calc());
  return person;
}

@Bean
public