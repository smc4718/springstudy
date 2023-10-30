package com.gdu.myhome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder      // 쉬운 생성을 도와주는 빌더패턴
public class CommentDto {
  private int commentNo;
  private String contents;
  private int blogNo;
  private String createdAt;
  private int status;
  private int depth;
  private int groupNo;
  private UserDto userDto;  // private int userNo;
}
