package com.gdu.myhome.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myhome.dto.AttachDto;
import com.gdu.myhome.dto.UploadDto;

@Mapper
public interface UploadMapper {
  public int insertUpload(UploadDto upload);
  public int insertAttach(AttachDto attach);
  public int getUploadCount();
  public List<UploadDto> getUploadList(Map<String, Object> map);  // 매퍼.xml 에서는 UploadMap 으로 적었지만, 자바가 인식하기는 UploadDto로 인식한다.
  public UploadDto getUpload(int uploadNo);
  public List<AttachDto> getAttachList(int uploadNo);
  public AttachDto getAttach(int attachNo);
  public int updateDownloadCount(int attachNo);
  public int updateUpload(UploadDto upload);
  public int deleteAttach(int attachNo);
}
