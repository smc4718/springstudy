<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

  <div>
    <h3>게시글 목록</h3>
    <c:forEach items="${boardList}" var="b">    <!-- 꺼내서 하나씩 저장하기 -->
      <div>               <!-- 상세보기는 상세보기할 번호의 전달이 필요하다. -->
        <a href="${contextPath}/board/detail.do?boardNo=${b.boardNo}">${b.boardNo} - ${b.title} - ${b.editor}</a> <!-- 3가지 정보를 한 줄로 나오게 연결 -->
      </div>
    </c:forEach>
  </div>

</body>
</html>