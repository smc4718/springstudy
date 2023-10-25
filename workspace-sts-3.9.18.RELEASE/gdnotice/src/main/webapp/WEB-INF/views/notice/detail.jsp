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
    fnRemove();
    fnList();
  })

  
  function fnRemove(){
	$('#btn_remove').click(function(){		
      if(confirm('게시글을 삭제할까요?')){
        location.href = '${contextPath}/notice/remove.do?no=${notice.no}';
      }
	})
  }
  
  function fnList(){
	$('.btn_list').click(function(){		
      location.href = '${contextPath}/notice/list.do';
	})
  }
  
</script>
</head>
<body>

  <div id="detail_screen">
    <h1>MvcBoard 게시글 상세보기화면</h1>
    <h3>${notice.no}번 게시글</h3>
    <div>작성자: ${notice.author}</div>
    <br>
    <div>작성일: ${notice.postDate}</div>
    <br>
    <div>작성IP: ${notice.ip}</div>
    <br>
    <div>조회수: ${notice.hit}</div>
    <br>
    <div>제목: ${notice.title}</div>
    <br>
    <div>내용</div><br>${notice.content}
    <div>
      <br>
      <button type="button" id="btn_remove">삭제하기</button>
      <button type="button" class="btn_list">목록보기</button>
    </div>
  </div>
  
  
</body>
</html>