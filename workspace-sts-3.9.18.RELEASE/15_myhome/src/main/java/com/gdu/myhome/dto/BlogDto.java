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
  private int hit;
  private String ip;
  private String createdAt;
  private String modifiedAt;
  private UserDto userDto;  // = private int userNo;  // 매퍼.xml에서 association 태그를 쓴 것은 맨 아래에 놓기.
}