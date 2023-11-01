<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="업로드게시글작성" name="title"/>
</jsp:include>

<div>

  <h1>Upload 게시글 작성하기</h1>
  
  <form method="post" action="${contextPath}/upload/add.do" enctype="multipart/form-data">
    <div>
      <label for="email">작성자</label>
      <input type="text" id="email" value="${sessionScope.user.email}" readonly> <!-- name이 없으면 서버로 보내지 않는다. upload 테이블에 사용자번호가 들어있지, 이메일 정보는 안들어있다. 다만 email은 화면에 표시만 해주는 용도이다. -->
    </div>
    <div>
      <label for="title">제목</label>
      <input type="text" name="title" id="title">
    </div>
    <div>
      <label for="contents">내용</label>
      <textarea rows="3" cols="50" name="contents" id="contents"></textarea>
    </div>
    <div>
      <label for="files">첨부</label>
      <input type="file" name="files" id="files" multiple> <!-- multiple 이 적혀있어야 다중첨부가 가능하다. -->
    </div>
    <div>
      <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
      <button type="submit">작성완료</button>
    </div>
  </form>
  
  <div id="file_list"></div>
  
</div>
  
<script>

  const fnFileCheck = () => {
    $('#files').change((ev) => {
      $('#file_list').empty();
      let maxSize = 1024 * 1024 * 100;
      let maxSizePerFile = 1024 * 1024 * 10;
      let totalSize = 0;
      let files = ev.target.files;
      for(let i = 0; i < files.length; i++){
        totalSize += files[i].size;
        if(files[i].size > maxSizePerFile){
          alert('각 첨부파일의 최대 크기는 10MB입니다.');
          $(ev.target).val('');
          $('#file_list').empty();
          return;
        }
        $('#file_list').append('<div>' + files[i].name + '</div>');
      }
      if(totalSize > maxSize){
        alert('전체 첨부파일의 최대 크기는 100MB입니다.');
        $(ev.target).val('');
        $('#file_list').empty();
        return;
      }
    })
  }
  
  fnFileCheck();
  
</script>
  
<%@ include file="../layout/footer.jsp" %>