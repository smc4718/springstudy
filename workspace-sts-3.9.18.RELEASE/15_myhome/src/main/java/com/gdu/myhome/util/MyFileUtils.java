package com.gdu.myhome.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtils {   // @Component 해놓고 불러다가 @Autowired 로 쓸 것.

  // 블로그 작성시 사용된 이미지가 저장될 경로 반환하기
  public String getBlogImagePath() {
    LocalDate today = LocalDate.now();
    return "/blog/" + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(today);
  }
  
  //블로그 이미지가 저장된 어제 경로를 반환
  public String getBlogImagePathInYesterday() {
    LocalDate date = LocalDate.now();
    date = date.minusDays(1);  // 1일 전
    return "/blog/" + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(date);
  }
  
  //업로드 게시판 작성시 첨부한 파일이 저장될 경로 반환하기
   public String getUploadPath() {
     LocalDate today = LocalDate.now();
     return "/upload/" + DateTimeFormatter.ofPattern("yyyy/MM/dd").format(today);
   }
  
  //임시 파일이 저장될 경로 반환하기 (zip 파일)
    public String getTempPath() {
      return "/temp";
    }
  
  // 파일이 저장될 이름 반환하기 (파일을 올릴 때 이름과, 실제 저장될 때 이름은 다르다)
  public String getFilesystemName(String originalFilename) { // Filesystem 은 한 단어임.
    
    /*  UUID.확장자  */
    
    String extName = null;
    if(originalFilename.endsWith("tar.gz")) { // 확장자에 마침표가 포함되는 예외 경우를 처리한다.
      extName = "tar.gz";
    } else {                                    // 자동완성시 regex 있으면 정규식 입력하라는 의미. 정규식에서 '.' 는 모든 문자라는 성격을 가지고 있음.
      String[] arr = originalFilename.split("\\.");  // 마침표를 문자로 만들어주려면, [.] 또는 \\. 으로 입력.
      extName = arr[arr.length-1];
    }   
    //   ↑↑↑ 확장자 만드는 식
    
    return UUID.randomUUID().toString().toString().replace("-", "") + "." + extName; // ← 원래 이름과 저장된 이름 구성하기.
  }
  
  
  //임시 파일 이름 반환하기 (확장자는 제외하고 이름만 반환)
   public String getTempFilename() {
     return System.currentTimeMillis() + "";
   }
  
  
 
  
}
