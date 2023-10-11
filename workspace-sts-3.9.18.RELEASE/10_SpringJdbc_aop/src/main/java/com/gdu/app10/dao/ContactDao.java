package com.gdu.app10.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.gdu.app10.dto.ContactDto;

import lombok.RequiredArgsConstructor;

//순서(중요) : ContactView -> ContactController -> ContactService -> ContactDao -> DB

/*
 * Dao
 * 1. Database Access Object
 * 2. 데이터베이스에 접근해서 쿼리문을 실행하고 쿼리문의 실행 결과를 받는 객체이다.
 * 3. 하나의 객체만 만들어서 사용하는 Singleton Pattern으로 객체를 생성한다.
 * (DB를 갔다 온 애들은 전부 dao 에 모아 둔다.)
 */

/*
 * Singleton Pattern
 * 1. 오직 하나의 객체만 만들 수 있도록 처리하는 패턴이다.
 * 2. 미리 하나의 객체를 만든 뒤 해당 객체를 가져다 사용할 수 있도록 처리한다.
 * 3. 객체 생성이 불가능하도록 생성자를 호출할 수 없게 만든다.
 * (dao의 외부에서는 new ContactDao() 를 사용하지 못하게 막는다.)
 */

@RequiredArgsConstructor
@Repository
public class ContactDao {
  
  @Autowired  // Spring Container에 저장된 JdbcConnection 타입의 객체(Bean)를 가져온다.
  private final JdbcTemplate jdbcTemplate;
  
  /**
   * 삽입메소드<br>
   * @param contactDto 삽입할 연락처 정보(name, tel, email, address)
   * @return insertCount 삽입된 행(Row)의 개수, 1이면 삽입 성공, 0이면 삽입 실패
   */
public int insert(final ContactDto contactDto) {  // 매개변수 변조를 막기 위해서 final 추가
  String sql = "INSERT INTO CONTACT_T(CONTACT_NO, NAME, TEL, EMAIL, ADDRESS, CREATED_AT) VALUES(CONTACT_SEQ.NEXTVAL, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))";
  int insertCount = jdbcTemplate.update(sql, new PreparedStatementSetter() {  // sql로 채워주고, PreparedStatementSetter 자동완성에 두 번째것으로 하면 인터페이스여도 new 가 가능함( Annoymous 선택하면 됨 )
    
    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
    // '?' 에 각 채워주는 것.
      ps.setString(1, contactDto.getName());
      ps.setString(2, contactDto.getTel());
      ps.setString(3, contactDto.getEmail());
      ps.setString(4, contactDto.getAddress());
    }
  }); 
  return insertCount;
}
  
/**
 * 수정 메소드<br>
 * @param contactDto 수정할 연락처 정보(contact_no, name, tel, email, address)
 * @return updateCount 수정된 행(Row)의 개수, 1이면 수정 성공, 0이면 수정 실패
 */
public int update(final ContactDto contactDto) {
 String sql = "UPDATE CONTACT_T SET NAME = ?, TEL = ?, EMAIL = ?, ADDRESS = ? WHERE CONTACT_NO = ?";    
 int updateCount = jdbcTemplate.update(sql, new PreparedStatementSetter() {
  
  @Override
  public void setValues(PreparedStatement ps) throws SQLException {
    ps.setString(1, contactDto.getName());
    ps.setString(2, contactDto.getTel());
    ps.setString(3, contactDto.getEmail());
    ps.setString(4, contactDto.getAddress());
    ps.setInt(5, contactDto.getContact_no());
    
  } 
 });
 return updateCount;
}
  
/**
 * 삭제 메소드<br>
 * @param contact_no 삭제할 연락처 번호
 * @return deleteCount 삭제된 행(Row)의 개수, 1이면 삭제 성공, 0이면 삭제 실패
 */
public int delete(final int contact_no) {
 String sql = "DELETE FROM CONTACT_T WHERE CONTACT_NO = ?";
 int deleteCount = jdbcTemplate.update(sql, new PreparedStatementSetter() {
  
  @Override
  public void setValues(PreparedStatement ps) throws SQLException {
    ps.setInt(1, contact_no);
    
  }
 });
 return deleteCount;
}

/**
 * 전체 조회 메소드<br>
 * @return 조회된 모든 연락처 정보(ContactDto)
 */
public List<ContactDto> selectList() {
 String sql = "SELECT CONTACT_NO, NAME, TEL, EMAIL, ADDRESS, CREATED_AT FROM CONTACT_T ORDER BY CONTACT_NO ASC";
 return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ContactDto.class)); // BeanProperty : 객체속성.  BeanPropertyRowMapper = 결과 값을 어떤 객체에 담을 겁니까?
}

/**
 * 상세 조회 메소드<br>
 * @param contact_no 조회할 연락처 번호
 * @return contactDto 조회된 연락처 정보, 조회된 연락처가 없으면 null 반환
 */
public ContactDto selectContactByNo(final int contact_no) {
 String sql = "SELECT CONTACT_NO, NAME, TEL, EMAIL, ADDRESS, CREATED_AT FROM CONTACT_T WHERE CONTACT_NO = ?";
 return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ContactDto.class), contact_no);
}

}