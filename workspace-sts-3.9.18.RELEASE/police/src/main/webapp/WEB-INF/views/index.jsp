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

  <!--  [ 데이터 전송 흐름 순서 ] :
     index.jsp -> NoticeController.java -> NoticeService.java 
     -> NoticeServiceImpl.java -> NoticeMapper.java -> noticeMapper.xml 
     코드로 구현하는 순서는 위의 흐름을 거꾸로 작성 해 나간다.            -->

  <div>
    <h3><a href="${contextPath}/notice/list.do">공지사항</a></h3>
  </div>  <!-- Spring MVC 에서만 ${contextPath} 적는 거고 스프링부트가면 생략됨. -->

</body>
</html>