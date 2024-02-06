package com.gdu.myhome.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.myhome.dto.FreeDto;

@Mapper
public interface FreeMapper {
  public int insertFree(FreeDto free);
  public int getFreeCount();
  public List<FreeDto> getFreeList(Map<String, Object> map);
  public int updateGroupOrder(FreeDto free); // free 이름은 자유
  public int insertReply(FreeDto free);
  public int deleteFree(int freeNo);
  public int getSearchCount(Map<String, Object> map); // column과 query를 하나로 저장한 Map.
  public List<FreeDto> getSearchList(Map<String, Object> map);
}