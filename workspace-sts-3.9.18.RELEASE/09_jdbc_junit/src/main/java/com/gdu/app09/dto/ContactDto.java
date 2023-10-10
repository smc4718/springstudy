package com.gdu.app09.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO : Database Transfer Object
// 유사어 - VO : Value Object ( Dto랑 같은 말, DB쪽이랑 연결되있으면 dto, 아니면 vo )

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactDto {

  private int contact_no;
  private String name;
  private String tel;
  private String email;
  private String address;
  private String created_at;
  
}