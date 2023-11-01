package com.gdu.myhome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder      // 쉬운 생성을 도와주는 빌더패턴
public class UploadDto {
  private int uploadNo;
  private String title;
  private String contents;
  private String createdAt;
  private String modifiedAt;
  private UserDto userDto;    // private int userNo 를 대체함.
}