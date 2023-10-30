package com.gdu.myhome.service;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myhome.dao.BlogMapper;
import com.gdu.myhome.dto.BlogDto;
import com.gdu.myhome.dto.BlogImageDto;
import com.gdu.myhome.dto.CommentDto;
import com.gdu.myhome.dto.UserDto;
import com.gdu.myhome.util.MyFileUtils;
import com.gdu.myhome.util.MyPageUtils;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {
  
  private final BlogMapper blogMapper;
  private final MyFileUtils myFileUtils;
  private final MyPageUtils myPageUtils;
  
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
                    .userDto(UserDto.builder()
                              .userNo(userNo)
                              .build())
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
  
  @Transactional(readOnly=true)
  @Override
  public void loadBlogList(HttpServletRequest request, Model model) {
    
    Optional<String> opt = Optional.ofNullable(request.getParameter("page")); // page 파라미터를 Optional로 감싸기.
    int page = Integer.parseInt(opt.orElse("1")); // 페이지 파라미터가 없으면 1을 대신 꺼내 쓰세요.
    int total = blogMapper.getBlogCount();
    int display = 10;
    
    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<BlogDto> blogList = blogMapper.getBlogList(map);
    
    model.addAttribute("blogList", blogList);
    model.addAttribute("paging", myPageUtils.getMvcPaging(request.getContextPath() + "/blog/list.do"));
    model.addAttribute("beginNo", total - (page - 1) * display); // 페이지당 첫 순서의 시작번호 계산.
    
  }
  
  @Override
  public int increaseHit(int blogNo) {
    return blogMapper.updateHit(blogNo);
  }
  
  @Override
  public BlogDto getBlog(int blogNo) {
    return blogMapper.getBlog(blogNo);
  }
  
  @Override
  public Map<String, Object> addComment(HttpServletRequest request) {
    
    String contents = request.getParameter("contents");
    int userNo = Integer.parseInt(request.getParameter("userNo"));
    int blogNo = Integer.parseInt(request.getParameter("blogNo"));
    
    CommentDto comment = CommentDto.builder()
                           .contents(contents)
                           .userDto(UserDto.builder()
                                     .userNo(userNo)
                                     .build())
                           .blogNo(blogNo)
                           .build();
    
    int addCommentResult = blogMapper.insertComment(comment);
    
    return Map.of("addCommentResult",addCommentResult);
  }
  
  @Override
  public Map<String, Object> loadCommentList(HttpServletRequest request) {

    int blogNo = Integer.parseInt(request.getParameter("blogNo"));
    
    int page = Integer.parseInt(request.getParameter("page"));
    int total = blogMapper.getCommentCount(blogNo);
    int display = 10;
    
    myPageUtils.setPaging(page, total, display);
    
    Map<String, Object> map = Map.of("blogNo", blogNo
                                   , "begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd());
    
    List<CommentDto> commentList = blogMapper.getCommentList(map);
    String paging = myPageUtils.getAjaxPaging();
    
    Map<String, Object> result = new HashMap<String, Object>();
    result.put("commentList", commentList);
    result.put("paging", paging);
    return result;
    
  }
  
  
}