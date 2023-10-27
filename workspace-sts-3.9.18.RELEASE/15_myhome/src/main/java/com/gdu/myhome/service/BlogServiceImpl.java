package com.gdu.myhome.service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.BlogMapper;
import com.gdu.myhome.dto.BlogDto;
import com.gdu.myhome.dto.BlogImageDto;
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
    
    // BlogDto 생성
    BlogDto blog = BlogDto.builder()
                    .title(title)
                    .contents(contents)
                    .userNo(userNo)
                    .ip(ip)
                    .build();
    
 // BLOG_T에 추가
    // BlogMapper의 insertBlog() 메소드를 실행하면
    // insertBlog() 메소드로 전달한 blog 객체에 blogNo값이 저장된다.
    int addResult = blogMapper.insertBlog(blog);
    
    // BLOG 작성시 사용한 이미지 목록 (Jsoup 라이브러리 사용)
    Document document = Jsoup.parse(contents);
    Elements elements =  document.getElementsByTag("img");
    
    if(elements != null) {
      for(Element element : elements) {
        String src = element.attr("src");
        String filesystemName = src.substring(src.lastIndexOf("/") + 1); 
        BlogImageDto blogImage = BlogImageDto.builder()
                                    .blogNo(blog.getBlogNo())
                                    .imagePath(myFileUtils.getBlogImagePath())
                                    .filesystemName(filesystemName)
                                    .build();
        blogMapper.insertBlogImage(blogImage);
      }
    }
    
    return addResult;
    
  }
  
  public void blogImageBatch() {
      
 // 1. 어제 작성된 블로그의 이미지 목록 (DB)
    List<BlogImageDto> blogImageList = blogMapper.getBlogImageInYesterday();
    
    // 2. List<BlogImageDto> -> List<Path> (Path는 경로+파일명으로 구성)                             * 패쓰형태 : /blog/2023/10/26
    List<Path> blogImagePathList = blogImageList.stream() // ↓ 해석 : blogImageDto를 경로하고 이름만 사용한 패쓰형태(경로형태)로 전부 다 바꿔서 리스트로 만들어달라.
                                                .map(blogImageDto -> new File(blogImageDto.getImagePath(), blogImageDto.getFilesystemName()).toPath())
                                                .collect(Collectors.toList());
    // 3. 어제 저장된 블로그 이미지 목록 (디렉토리)
    File dir = new File(myFileUtils.getBlogImagePathInYesterday());
    
    // 4. 삭제할 File 객체들   // ↓ 해석 : 디렉토리에 저장된 모든 애들을 파일이라고 하고 하나씩 불러서 경로에 포함되어있는지 확인하고 true인 대상들만 넘겨줄 것이다.
    File[] targets = dir.listFiles(file -> !blogImagePathList.contains(file.toPath()));
                                  //   ↑ 람다식으로 함 (for문으로 하면 구문이 길어지고 이상해짐)
    // 5. 삭제
    if(targets != null && targets.length !=0) {
      for(File target : targets) {
        target.delete();
      }
    }
  
  }
  
}