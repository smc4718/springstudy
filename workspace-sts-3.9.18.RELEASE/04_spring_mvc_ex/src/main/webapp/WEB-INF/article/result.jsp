<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

 <!-- model 에 저장할 이름을 그대로 적어주기.(많이 사용 안 되는 방식) -->
  <div>
    <div>${articleNo}</div>
    <div>${title}</div>
    <div>${content}</div>
  </div>
  
  <hr>
  
  <!-- model 에 객체 단위로 저장해서 넘기는 방식.(이게 더 많이 사용됨) -->
  <div>
    <div>${vo.articleNo}</div>
    <div>${vo.title}</div>
    <div>${vo.content}</div>
  </div>
  
  <hr>
  
  <!-- 커맨드객체로 저장된 방식 ( 자동으로 model에 저장됨 ) -->
  <div>
    <div>${articleVo.articleNo}</div>
    <div>${articleVo.title}</div>
    <div>${articleVo.content}</div>
  </div>
  
  
  
  <div>
    <div>${atcVo.articleNo}</div>
    <div>${atcVo.title}</div>
    <div>${atcVo.content}</div>
  </div>

</body>
</html>