package com.gdu.app13.util;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtil {   // @Component 해놓고 불러다가 @Autowired 로 쓸 것.

  // 파일이 저장될 경로 반환하기
  public String getPath() {
    /*  /storage/yyyy/MM/dd */
    LocalDate today = LocalDate.now();  // 오늘을 구하는 방법
    return "/storage/" + today.getYear() + "/" + String.format("%02d", today.getMonthValue()) + "/" + String.format("%02d", today.getDayOfMonth());   // 2자리 안되면, 0 으로 채워라.
 // return "/storage/" + DateTimeFormatter.ofPattern("yyyy/MM/DD").format(today);
  }
  
  // 파일이 저장될 이름 반환하기 (파일을 올릴 때 이름과, 실제 저장될 때 이름은 다르다)
  public String getFilesystemName(String originalName) { // Filesystem 은 한 단어임.
    
    /*  UUID.확장자  */
    
    String extName = null;
    if(originalName.endsWith("tar.gz")) { // 확장자에 마침표가 포함되는 예외 경우를 처리한다.
      extName = "tar.gz";
    } else {                                    // 자동완성시 regex 있으면 정규식 입력하라는 의미. 정규식에서 '.' 는 모든 문자라는 성격을 가지고 있음.
      String[] arr = originalName.split("\\.");  // 마침표를 문자로 만들어주려면, [.] 또는 \\. 으로 입력.
      extName = arr[arr.length-1];
    }   
    //   ↑↑↑ 확장자 만드는 식
    
    return UUID.randomUUID().toString().toString().replace("-", "") + "." + extName; // ← 원래 이름과 저장된 이름 구성하기.
  }
  
  
}
