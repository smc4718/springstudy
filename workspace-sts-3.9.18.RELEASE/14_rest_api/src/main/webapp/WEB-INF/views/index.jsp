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
<script>
  
  $(function(){
    fnMemberRegister();
  })
  
  // 회원 등록
  function fnMemberRegister(){
  	$.ajax({
  		// 요청
  		type: 'post',
  		url: '${contextPath}/members',
  		contentType: 'application.json'
  		data: JSON.stringify({
  		  id: 'admin', 	 // id 임의로 넣었음
  		  name: '관리자' // name 임의로 넣었음
  		  gender: 'woman',
  		  address: 'seoul'
  		}),
  		// 응답
  		dataType: 'json',
  		success: function(resData){
  			console.lof(resData);
  		}
  	})
  }  
  
</script>  

</body>
</html>