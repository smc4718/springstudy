package com.gdu.app13.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class AppConfig {

  @Bean
  public MultipartResolver multipartResolver() {  // 항상 인터페이스가 있으면 인터페이스 타입으로 잡기.
    CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
    commonsMultipartResolver.setDefaultEncoding("UTF-8");
    commonsMultipartResolver.setMaxUploadSize(1024 * 1024 * 100); // 전체 첨부 파일의 크기 100메가 = 1mg x 1mg x 100 : 100메가.
    commonsMultipartResolver.setMaxUploadSizePerFile(1024 * 1024 * 10); // 개별 첨부 파일의 최대 크기 10메가 = 1mg x 1mg x 10 :  10메가.
    return commonsMultipartResolver;
  }
  
}
