package com.gdu.app02.anno01;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public Calculator calc() {
    return new Calculator();
  }
  
  // Member
  public Member member() {
    Member m = new Member();
    m.setName("톰");
    m.setHeight(175.1);
    m.setWeight(65);
    m.setCalculator(calc());
    double w = m.getWeight();
    double h = m.getHeight();
    Calculator c = m.getCalculator();
    m.setBmi( c.div(w, c.div(c.mul(h, h), 10000)) );
    double bmi = m.getBmi();
    m.setStatus(bmi < 20 ? "저체중" : bmi > 25 ? : "정상" : bmi < 30? "과체중" : "비만");
    return m;
  }
  
  // Fitness
  @Bean
  public Fitness fitness() {
    Fitness f = new Fitness();
    f.setName("가산피트니스");
    f.setMembers(Arrays.asList(member()));;
  }
  
}
