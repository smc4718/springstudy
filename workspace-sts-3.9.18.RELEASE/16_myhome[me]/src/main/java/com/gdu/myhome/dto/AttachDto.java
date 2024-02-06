package com.gdu.myhome.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder      // 쉬운 생성을 도와주는 빌더패턴
public class AttachDto {
  private int attachNo;
  private String path;
  private String originalFilename;
  private String filesystemName;
  private int downloadCount;
  private int hasThumbnail;
  private int uploadNo;
}