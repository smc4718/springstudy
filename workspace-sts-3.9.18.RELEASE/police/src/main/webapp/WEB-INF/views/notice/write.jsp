<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

  <div>
    <h1>공지 작성화면</h1>
    <form method="post" action="${contextPath}/notice/save.do">
      <select name="gubun">  <!-- name 만 gubun이라고 잘 되어있으면 다 잘 된다. -->
        <option value="2">일반</option>
        <option value="1">긴급</option>
      </select>
      <input type="text" name="title">
      <input type="text" name="content">
      <button>작성완료</button> <!-- 버튼의 타입은 생략해도 자동으로 Submit 한다. -->
      <!-- Dto gubun, title, content 필드가 있고 name이 맞으면 이 3개를 모아서 Controller의 noticeDto 로 데이터가 가진다. -->
    </form>
    
  </div>
  
</body>
</html>