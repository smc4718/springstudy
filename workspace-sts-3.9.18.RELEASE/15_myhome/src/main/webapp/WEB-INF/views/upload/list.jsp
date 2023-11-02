<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="업로드게시판" name="title"/>
</jsp:include>

<!-- "내용물만 200이다." 라고 지정하는 방식은 box-sizing: content-box: 이고, "전체 박스크기가 200이다." 라고 하려면 box-sizing: border-box: 로 한다. -->
<!-- margin: 상하여백10px 좌우는auto -->
<!-- 기본 flex는 한 줄에 9개씩 빼곡하게 찬다. 기본값은 flex-wrap:nowrap: 이다. (크기 무시하고 우겨넣음), 그러지 말라고 flex-wrap:wrap: 으로 바꿔주어야 크기 모자르면 다음으로 넘어간다. -->
<!-- padding-top: 100px;  => 상단 여백 100px -->
<!-- margin: 위아래20px, 좌우 15px -->
<style>
  div {
    box-sizing: border-box;
  }
  .upload_list {
    width: 1000px;
    margin: 10px auto;  
    display: flex;
    flex-wrap: wrap;
  }
  .upload {
    width: 300px;
    height: 300px;
    border: 1px solid gray;
    text-align: center;
    padding-top: 100px;
    margin: 20px 15px;
  }
  .upload:hover {
    background-color: silver
    cursor: pointer;
  }
</style>


<div>

  <div>
    <a href="${contextPath}/upload/write.form">
      <button type="button" class="btn btn-primary">새글작성</button>
    </a>
  </div>
  
  <div id="upload_list" class="upload_list"></div>

</div>

<script>
	
	// 전역 변수
	var page = 1;
	var totalPage = 0;
	
	const fnGetUploadList = () => {
		  $.ajax({
			  // 요청
			  type: 'get',
			  url: '${contextPath}/upload/getList.do',
			  data: 'page=' + page,
			  // 응답
			  dataType: 'json',
			  success: (resData) => {  // resData = {"uploadList": [], "totalPage": 10}
				  totalPage = resData.totalPage;
			    $('#upload_list').empty();
			    $.each(resData.uploadList, (i, upload) => {
			    	let str = '<div class="upload">';
			    	str += '<div>제목: ' + upload.title + '</div>';
			    	str += '<div>작성: ' + upload.userDto.name + '</div>'; // 작성자는 userDto에 있는 name
			    	str += '<div>생성: ' + upload.createdAt + '</div>';    // 작성일자
			    	str += '<div>첨부: ' + upload.attachCount + '개</div>';
			    	str += '</div>';
			    	$('#upload_list').append(str);
			    })
			  }
		  })
	  }
	
	
	const fnScroll = () => {
		
	  var timerId;	// 최초 undefined 상태.
	  
	  $(window).on('scroll', () => {	// window 객체가 스크롤을 관장한다.  윈도우가 스크롤 되었을 때.
	
		if(timerId){	// timerId가 undefined이면 false로 인식, timerId가 값을 가지면 true로 인식
	      clearTimeout(timerId);	
		}  
		  
		timerId = setTimeout(() => {  // setTimeout 실행 전에는 timerId가 undefined 상태, setTimeout이 한 번이라도 동작하면 timerId가 값을 가짐.
		  
		 // 반드시 작성해야 하는 3가지 ↓
		  let scrollTop = $(window).scrollTop();  // 스크롤바 위치(스크롤 된 길이)	   // ← 이 줄부터 내가 하고 싶은 일 적으면 된다.
		  let windowHeight = $(window).height();  // 화면 전체 크기
		  let documentHeight = $(document).height();  // 문서 전체 크기
		  
		  if((scrollTop + windowHeight + 100) >= documentHeight) {   // 스크롤이 바닥에 닿기 50px 전에 true가 됨.
			if(page > totalPage) {  // 마지막 페이지를 보여준 이후에 true가 됨.
			  return;   // 마지막 페이지를 보여준 이후에는 아래 코드를 수행하지 말 것 (return의 의미 : return 이후의 코드를 수행하지 마라)
			}
		  	page++;  // 다음페이지로 넘어감.
		  	fnGetUploadList();	// 다음페이지 목록 갱신
		  }
		  
		}, 200);	// 200밀리초(0.2초) 후 동작
		
	  })
	  
	}
	



	const fnAddResult = () => {
	  let addResult = '${addResult}';	// '', 'true', 'false'
	  if(addResult != ''){
		 if(addResult === 'true'){
			alert('성공적으로 업로드 되었습니다.');
			$('#upload_list').empty();
			fnGetUploadList();
		 } else {
		   alert('업로드가 실패하였습니다.');
		 }
	  }
	  
	}
	
	// 호출
	fnGetUploadList();
	fnScroll();
	fnAddResult();

</script>

<%@ include file="../layout/footer.jsp" %>