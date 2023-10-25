package com.gdu.myapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdu.myapp.dto.NoticeDto;

@Repository
public class NoticeDao {
  
  @Autowired  // Spring Container에 저장된 JdbcConnection 타입의 객체(Bean)를 가져온다.
  private JdbcConnection jdbcConnection;
  
  private Connection con;
  private PreparedStatement ps;
  private ResultSet rs;
  private String sql;
  
  private Connection getConnection() throws Exception {
    Class.forName("oracle.jdbc.OracleDriver");
    return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "GD", "1111");
  }
  
  private void close() {
    try {
      if(rs != null) rs.close();
      if(ps != null) ps.close();
      if(con != null) con.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public List<NoticeDto> getNoticeList() {
    List<NoticeDto> list = new ArrayList<NoticeDto>();
    try {
      con = getConnection();
      sql = "SELECT NO, AUTHOR, TITLE, CONTENT, HIT, IP, POSTDATE FROM MVC_BOARD_T ORDER BY NO DESC";
      ps = con.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next()) {
        NoticeDto notice = new NoticeDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getDate(7));
        list.add(notice);
      }
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      close();
    }
    return list;
  }
  
  public NoticeDto getNotice(int no) {
    NoticeDto notice = null;
    try {
      con = getConnection();
      sql = "SELECT NO, AUTHOR, TITLE, CONTENT, HIT, IP, POSTDATE FROM MVC_BOARD_T WHERE NO = ?";
      ps = con.prepareStatement(sql);
      ps.setInt(1, no);
      rs = ps.executeQuery();
      if(rs.next()) {
        notice = new NoticeDto(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6), rs.getDate(7));
      }
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      close();
    }
    return notice;
  }
  
public int addNotice(NoticeDto noticeDto) {
    
    int insertCount = 0;
    
    try {
      
      con = jdbcConnection.getConnection();
      // 쿼리문은 항상 SQL 파일에서 돌려보고 되면 이곳에 복붙하기.
      String sql = "INSERT INTO MVC_BOARD_T(NO, AUTHOR, TITLE, CONTENT, HIT, IP, POSTDATE) VALUES(MVC_BOARD_SEQ.NEXTVAL, ?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD'))";
      ps = con.prepareStatement(sql);
      ps.setString(1, noticeDto.getAuthor());
      ps.setString(2, noticeDto.getTitle());
      ps.setString(3, noticeDto.getContent());
      ps.setInt(4, noticeDto.getHit());
      ps.setString(5, noticeDto.getIp());
      insertCount = ps.executeUpdate();
      
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      jdbcConnection.close(con, ps, rs);
    }
    
    return insertCount;
    
  }

  public int removeNotice(int no) {
    int result = 0;
    try {
      con = getConnection();
      sql = "DELETE FROM MVC_BOARD_T WHERE NO = ?";
      ps = con.prepareStatement(sql);
      ps.setInt(1, no);
      result = ps.executeUpdate();
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      close();
    }
    return result;
  }
  
}
