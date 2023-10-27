package com.gdu.myhome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder      // 쉬운 생성을 도와주는 빌더패턴
public class BlogDto {
  private int blogNo;
  private String title;
  private String contents;
  private int userNo;      // 사용자와의 관계를 userNo 로 맺었음
  private int hit;
  private String ip;
  private String createdAt;
  private String modifiedAt;
}
