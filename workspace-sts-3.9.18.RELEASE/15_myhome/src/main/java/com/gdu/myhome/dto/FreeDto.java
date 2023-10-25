package com.gdu.myhome.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreeDto {
  private int freeNo;
  private String email;
  private String contents;
  private Timestamp createdAt; // 자바의 Timestamp 는 sql로 해야 한다.
  private int status;
  private int depth;
  private int groupNo;
  private int groupOrder;
}
