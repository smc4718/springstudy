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
<script>

  $(function(){
    fnInit();
  })
  
  function fnInit(){
    var addResult = '${addResult}';
    if(addResult != '') {
      if(addResult == '1'){
        alert('게시글이 등록되었습니다.');
      } else {
        alert('게시글이 등록되지 않았습니다.');
      }
    }
    var removeResult = '${removeResult}';
    if(removeResult != '') {
      if(removeResult == '1'){
        alert('게시글이 삭제되었습니다.');
      } else {
        alert('게시글이 삭제되지 않았습니다.');
      }
    }
  }
  
</script>
</head>
<body>

  <div>
    <a href="${contextPath}/notice/write.do">새글작성</a>
  </div>

  <hr>
  
  <div>
    <table border="1">
      <thead>
        <tr>
          <td>글번호</td>
          <td>제목</td>
          <td>작성자</td>
          <td>작성일</td>
          <td>조회수</td>
        </tr>
      </thead>
      <tbody>
        <c:if test="${empty noticeList}">
          <tr>
            <td colspan="2">등록된 공지사항이 없습니다.</td>
          </tr>
        </c:if>
        <c:if test="${not empty noticeList}">
          <c:forEach items="${noticeList}" var="notice">
            <tr>
              <td>${notice.no}</td>
              <td><a href="${contextPath}/notice/detail.do?no=${notice.no}">${notice.title}</a></td>
              <td>${notice.author}</td>
              <td>${notice.postDate}</td>
              <td>${notice.hit}</td>
            </tr>
          </c:forEach>
        </c:if>
      </tbody>
    </table>
  </div>
  
  <hr>
  
  
</body>
</html>