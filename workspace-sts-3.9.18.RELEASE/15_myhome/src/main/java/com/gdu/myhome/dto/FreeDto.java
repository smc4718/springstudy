package com.gdu.myhome.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder    // 쉬운 생성을 도와주는 빌더패턴
public class FreeDto {
  private int freeNo;
  private String email;   // 사용자와의 관계를 이메일로 맺었음
  private String contents;
  private Timestamp createdAt; // 자바의 Timestamp 는 sql로 해야 한다.
  private int status;
  private int depth;
  private int groupNo;
  private int groupOrder;
}
