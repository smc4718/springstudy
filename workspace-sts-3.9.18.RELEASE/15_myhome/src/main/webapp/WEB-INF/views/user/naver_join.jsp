<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="네이버간편가입" name="title"/>
</jsp:include>

<script>
  
  $(() => {
    fnNaverJoin();
  })
  
  const fnNaverJoin = () => {
    $('#frm_naver_join').submit((ev) => {
      if(!$('#service').is(':checked')){ // 체크가 안돼있다면.
        alert('이용약관에 동의하세요.');
        ev.preventDefault(); // submit 취소
        return;
      }
    })
  }
  
</script>

<div>

  <form id="frm_naver_join" method="post" action="${contextPath}/user/naver/join.do">
    
    <h1>네이버간편가입</h1>
    
    <div>
      <label for="email">이메일</label>              <!-- 정보를 바꾸지 못하게 readonly 처리 -->
      <input type="text" name="email" id="email" value="${naverProfile.email}" readonly>
    </div>
  
    <div>
      <label for="name">이름</label>              <!-- 정보를 바꾸지 못하게 readonly 처리 -->
      <input type="text" name="name" id="name" value="${naverProfile.name}" readonly>
    </div>
  
    <div>
      <input type="radio" name="gender" value="M" id="man">
      <label for="man">남자</label>
      <input type="radio" name="gender" value="F" id="woman">
      <label for="woman">여자</label>
    </div>
    <!-- 프로필에 있는 값으로 표시하기(스크립트 짜야함) -->
    <script>
      // 네이버프로필에 value부분이 F냐 M이냐에 따라서, 그것으로 체크하겠다.
      $(':radio[value=${naverProfile.gender}]').prop('checked', true);
    </script>
  
    <div>
      <label for="mobile">휴대전화번호</label>
      <input type="text" name="mobile" id="mobile" value="${naverProfile.mobile}" readonly>
    </div>

    <hr>
    
    <div>
      <input type="checkbox" name="service" id="service">
      <label for="service">서비스 이용약관 동의(필수)</label>
    </div>
    <div>
      <textarea>본 약관은 ...</textarea>
    </div>
    
    <div>
      <input type="checkbox" name="event" id="event">
      <label for="event">이벤트 알림 동의(선택)</label>
    </div>
    <div>
      <textarea>본 약관은 ...</textarea>
    </div>
    
    <hr>

    <div>
      <button type="submit">회원가입하기</button>
    </div>
    
  </form>

</div>

<%@ include file="../layout/footer.jsp" %>