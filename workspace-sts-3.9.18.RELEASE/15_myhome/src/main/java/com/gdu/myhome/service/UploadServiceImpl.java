package com.gdu.myhome.service;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.UploadMapper;
import com.gdu.myhome.dto.UploadDto;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyFileUtils;
import com.gdu.myhome.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

  private final UploadMapper uploadMapper;  // 매퍼 이용할 용도
  private final MyFileUtils myFileUtils;    // 파일첨부할 용도
  private final MyPageUtils myPageUtils;    // 목록 다룰 용도
  
  @Override
  public int addUpload(MultipartHttpServletRequest multipartRequest) throws Exception {
    
    String title = multipartRequest.getParameter("title");
    String contents = multipartRequest.getParameter("contents");
    int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
    
    UploadDto upload = UploadDto.builder()
                        .title(title)
                        .contents(contents)
                        .userDto(UserDto.builder()
                                  .userNo(userNo)
                                  .build())
                        .build();
                        
  // int addUploadResult = uploadMapper.insertUpload(upload);
    
    List<MultipartFile> files = multipartRequest.getFiles("files");     //첨부된 파일을 'MultipartFile' 이라고 부른다. multiple은 첨부파일이 여러개이기 때문에 List로 잡아야 한다.
    
    for(MultipartFile multipartFile : files) {
      if(multipartFile != null && !multipartFile.isEmpty()) {
        
        String path = myFileUtils.getUploadPath();
        File dir = new File(path);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        String originalFilename = multipartFile.getOriginalFilename();
        String filesystemName = myFileUtils.getFilesystemName(originalFilename);  // 원래 이름에 확장자를 그대로 사용하기 위해 전달해주는 작업이 필요함.
        File file = new File(dir, filesystemName);
        
        multipartFile.transferTo(file);
        
      }
    }
    return 0;
  }
  
}
