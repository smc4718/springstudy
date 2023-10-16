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

  <div ia="a">
    <h1>공지 상세화면</h1>
    <h3>공지번호 : ${notice.noticeNo}</h3>
    <h3>구분 : ${notice.gubun == 1 ? '긴급' : '일반'}</h3>  <!-- 삼항연산자 사용 가능 -->
    <h3>제목 : ${notice.title}</h3>
    <h3>내용 : ${notice.content}</h3>
    <!-- 버튼은 아래 값들보다 상단에 있어야 한다. -->
    <div>
      <button type="button" id="btn_edit">편집하러가기</button>
    </div>
  </div>
  
  <div id="b">
    <div>
      <button type="button" id="btn_back">뒤로가기</button>
    </div>
    <form method="post" action="${contextPath}/notice/modify.do">
      <select name="gubun" id="gubun">  <!-- name 만 gubun이라고 잘 되어있으면 다 잘 된다. -->
        <option value="2">일반</option>
        <option value="1">긴급</option>
      </select>
      <input type="text" name="title" id="title">
      <input type="text" name="content" id="content">
      <input type="hidden" name="noticeNo" id="noticeNo">
      <button>편집완료</button> <!-- 버튼의 타입은 생략해도 자동으로 Submit 한다. -->
      <!-- Dto gubun, title, content 필드가 있고 name이 맞으면 이 3개를 모아서 Controller의 noticeDto 로 데이터가 가진다. -->
    </form>
    
    <script>	
        // 수정(편집) 기능.
    	// 수정(편집) 할 수 있는 기능을 스크립트로 만들어 보자.
    	// 가진 값을 가져와서 보여줘라.
    	$('#gubun').val('${notice.gubun}')	// notice 가 가진 gubun값을 바꿔라.
    	$('#title').val('${notice.title}')	// notice 가 가진 title값을 바꿔라.
    	$('#content').val('${notice.content}')	// notice 가 가진 content값을 바꿔라.
    	$('#noticeNo').val('${notice.noticeNo}')	// notice 가 가진 noticeNo 값을 바꿔라.
    </script>
   </div>
   
   <script>
   // 모든 곳에 적용되기 위해 div 바깥에 스크립트를 작성해줌.
   
    // 초기화면
   	$('#a').show(); // 평소에 a는 표시한다.
   	$('#b').hide(); // 평소에 b는 숨긴다.
   	
   	// 편집하러가기 클릭 (클릭시 반대로 바꿈)
   	$('#btn_edit').click(function(){
   		$('#a').hide();
   		$('#b').show();
   	})
   	
   	// 뒤로가기 클릭 ( a를 다시 보여주고 b를 숨긴다.)
   	$('#btn_back').click(function(){
   		$('#a').show(); 
   	   	$('#b').hide();
   	})
   	
   	var modifyResult = '${modifyResult}';	// ''. '1', '0'
   	if(modifyResult !== ''){
   		if(modifyResult === '1'){
   			alert('공지사항이 수정되었습니다.');
   		} else {
   			alert('공지사항이 수정되지 않았습니다.');
   		}
   	}
   	
   </script>
  
</body>
</html>