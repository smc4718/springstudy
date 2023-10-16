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
	
	// 스크립트에 function 없이 적으면 곧바로 동작하는 겁니다.
	// 그동안은 function 연습하느라 만들고 호출을 따로 했던 겁니다.
	var addResult = '${addResult}';	// addResult가 가질 수 있는 값 : '', '1', '0' 
	if(addResult !== ''){	// 비어있으면 안되니까 적어 준다.
	   if(addResult === '1'){
		   alert('공지사항이 등록되었습니다.');
	   } else {
		   alert('공지사항이 등록되지 않았습니다.');
	   }
	}

</script>
</head>
<body>

  <div>
    
    <!-- 작성화면 -->
    <!-- 화면이동만 할 것이다. -->
    <a href="${contextPath}/notice/write.do">공지 작성하러 가기</a>
    
    
    <!-- 목록 만들기 -->
    <!--  하나씩 꺼내서 쓰고싶을 때, var 로 이름을 주면 된다. -->
    <c:forEach items="${noticeList}" var="n">  <!-- ${noticeList} 는 NoticeController.java의 "List<NoticeDto> noticeList" 이다. -->
      <div>
        <h3>공지번호 : ${n.noticeNo}</h3>
        <h3>구분 : ${n.gubun}</h3>
        <h3>제목 : ${n.title}</h3>
      </div>
      <hr>
    </c:forEach>
  </div>
  
</body>
</html>