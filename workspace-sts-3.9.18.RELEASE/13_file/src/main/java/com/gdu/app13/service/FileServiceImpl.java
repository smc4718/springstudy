package com.gdu.app13.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.app13.util.MyFileUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

  private final MyFileUtil myFileUtil;
  
  @Override
  public int upload(MultipartHttpServletRequest multipartRequest) {

    // 첨부된 파일들의 목록
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    // 순회
    for(MultipartFile multipartFile : files) {
      
      // 첨부 여부 확인
      if(multipartFile != null && !multipartFile.isEmpty()) {
        
        try {
          
          // 첨부 파일이 저장될 경로 가져오기
          String path = myFileUtil.getPath();
          
          // 저장될 경로의 디렉터리 만들기
          File dir = new File(path);
          if(!dir.exists()) {
            dir.mkdirs();
          }
          
          // 첨부 파일의 원래 이름 알아내기
          String originalName = multipartFile.getOriginalFilename();
          
          // 첨부 파일이 저장될 이름 가져오기
          String filesystemName = myFileUtil.getFilesystemName(originalName);
          
          // 첨부 파일의 File 객체
          File file = new File(dir, filesystemName);
          
          // 첨부 파일 저장하기
          multipartFile.transferTo(file);
          
        } catch (Exception e) {
          e.printStackTrace();
        }
        
      }
      
    }
    
    return 0;
    
  }
  
  @Override
  public Map<String, Object> ajaxUpload(MultipartHttpServletRequest multipartRequest) {
    
    // 첨부된 파일들의 목록
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    // 순회
    for(MultipartFile multipartFile : files) {
      
      // 첨부 여부 확인
      if(multipartFile != null && !multipartFile.isEmpty()) {
        
        try {
          
          // 첨부 파일이 저장될 경로 가져오기
          String path = myFileUtil.getPath();
          
          // 저장될 경로의 디렉터리 만들기
          File dir = new File(path);
          if(!dir.exists()) {
            dir.mkdirs();
          }
          
          // 첨부 파일의 원래 이름 알아내기
          String originalName = multipartFile.getOriginalFilename();
          
          // 첨부 파일이 저장될 이름 가져오기
          String filesystemName = myFileUtil.getFilesystemName(originalName);
          
          // 첨부 파일의 File 객체
          File file = new File(dir, filesystemName);
          
          // 첨부 파일 저장하기
          multipartFile.transferTo(file);
          
        } catch (Exception e) {
          e.printStackTrace();
        }
        
      }
      
    }
    
    return Map.of("success", true);
    
  }  

  @Override
  public Map<String, Object> ckeditorUpload(MultipartFile upload, String contextPath) {
    
    // 이미지 저장할 경로
    String path = myFileUtil.getPath();
    File dir = new File(path);
    if(!dir.exists()) {
      dir.mkdirs();
    }
    
    // 이미지 저장할 이름(원래 이름 + 저장할 이름)
    String originalFilename = upload.getOriginalFilename(); // 원래 이름
    String filesystemName = myFileUtil.getFilesystemName(originalFilename); // 저장할 이름 = 최종적으로 필요한 이름
    
    // 이미지 File 객체
    File file = new File(dir, filesystemName);
    
    // File 객체를 참고하여, MultipartFile upload 첨부 이미지 저장
    try {
      upload.transferTo(file); // file객체 참고해서 업로드를 file객체정보(디렉토리, 파일이름)로 보내라.
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    
    // CKEditor로 저장된 이미지를 확인할 수 있는 경로를 {url: http://localhost:8080/...} 방식으로 반환해야 한다(정해져 있는 것).
    return Map.of("url", contextPath + path + "/" + filesystemName   // json 변환이 가능한 Map을 사용한다. 
                , "uploaded", true);
    
    // 이렇게 직접 작성할 수도 있다. contextPath + "/display.do?path=" + path + "&filename=" + filesystemName 
  }
  
    /*
     *  CKEditor로 반환할 Url
     *    http://localhost:8080/app13/storage/2023/10/18/762e811f0597476ca9adfae76febb4d4.jpg
     *
     * servlet-context.xml에
     *  /storage/** 주소로 요청을 하면 /storage/ 디렉터리의 내용을 보여주는 <resources> 태그를 추가한다.
     *  
     * ( servlet-context.xml에서 <<resources mapping="/storage/**" location="file:///storage/" /> 를 작성 해 준다. )
     *   
     */
  
  
}