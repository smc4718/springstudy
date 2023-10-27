package com.gdu.myhome.service;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.BlogMapper;
import com.gdu.myhome.dto.BlogDto;
import com.gdu.myhome.util.MyFileUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {
  
  private final BlogMapper blogMapper;
  private final MyFileUtils myFileUtils;
  
  @Override
  public Map<String, Object> imageUpload(MultipartHttpServletRequest multipartRequest) {
   
    // 이미지가 저장될 경로
    String imagePath = myFileUtils.getBlogImagePath();
    File dir = new File(imagePath);
    if(!dir.exists()) {
      dir.mkdirs();
    }
    
    // 이미지 파일 (CKEditor는 이미지를 upload 라는 이름으로 보냄)
    MultipartFile upload = multipartRequest.getFile("upload");
    
    // 이미지가 저장될 이름
    String originalFilename = upload.getOriginalFilename();
    String filesystemName = myFileUtils.getFilesystemName(originalFilename);  // 저장될 이름을 제공한다.
    
    // 이미지 File 객체
    File file = new File(dir, filesystemName);   // 경로는 dir , 저장된 이름은 filesystemName
    
    // 저장
    try {
      upload.transferTo(file);  // file을 보내라.
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    // CKEditor로 저장된 이미지의 경로를 JSON 형식으로 반환해야 함
    return Map.of("uploaded", true  // 업로드 되었다, 그렇다.
                , "url", multipartRequest.getContextPath() + imagePath + "/" + filesystemName);  // 이미지가 저장될 경로. 
  
    // url: "http://localhost:8080/myhome/blog/2023/10/27/파일명"  <- CKEditor로 이 주소를 보냈다.
    // servlet-context.xml에
    // /blog/** 주소 요청을 실제 저장될 디렉토리인  /blog 디렉터리로 연결하는 <resources> 태그를 추가해야 함.
  
  }
  
  @Override
  public int addBlog(HttpServletRequest request) {
    
    String title = request.getParameter("title");
    String contents = request.getParameter("contents");
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    String ip = request.getRemoteAddr();
    
    BlogDto blog = BlogDto.builder()
                    .title(title)
                    .contents(contents)
                    .userNo(userNo)
                    .ip(ip)
                    .build();
    
    int addResult = blogMapper.insertBlog(blog);
    
    return addResult;
    
  }
  
}
