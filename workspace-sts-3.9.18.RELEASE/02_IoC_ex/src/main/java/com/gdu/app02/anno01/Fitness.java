package com.gdu.app02.anno01;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Fitness {
  
  private String name;
  private Calculator calculator;
  private List<Member> members;
  
  public void info() {
    for (Member member : members) {
      System.out.println("이름: " + member.getName());
      System.out.println("키: " + member.getHeight());
      System.out.println("몸무게: " + member.getWeight());
      System.out.println("BMI: " + member.getBmi() + "(" + member.getStatus() + ")");
    }
  }
  
}
