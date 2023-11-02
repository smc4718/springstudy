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
<style>
  div {
    box-sizing: border-box;
  }
  .upload_list {
    width: 1000px;
    margin: 10px auto;  
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  .upload {
    width: 300px;
    height: 300px;
    border: 1px solid gray;
    text-align: center;
    padding-top: 100px;
    margin-top: 20px;
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




	const fnAddResult = () => {
	  let addResult = '${addResult}';	// '', 'true', 'false'
	  if(addResult != ''){
		 if(addResult === 'true'){
			alert('성공적으로 업로드 되었습니다.');
			fnGetUploadList();
		 } else {
		   alert('업로드가 실패하였습니다.');
		 }
	  }
	  
	}
	
	// 호출
	fnGetUploadList();
	fnAddResult();

</script>

<%@ include file="../layout/footer.jsp" %>