<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="${upload.uploadNo}번 게시글" name="title"/>
</jsp:include>

<style>
  .attach {
    cursor: pointer;
  }
</style>

<div>

  <h1 style="text-align: center;">Upload 게시글</h1>
  <div>
    <form id="frm_edit" method="post">   <!-- 버튼을 모아두는 폼 -->
      <div>작성자 : ${upload.userDto.name}</div>
      <div>작성일 : ${upload.createdAt}</div>
      <div>수정일 : ${upload.modifiedAt}</div>
      <div>제목 : <input type="text" name="title" value="${upload.title}"></div>
      <div>내용</div>
      <div><textarea name="contents">${upload.contents}</textarea></div>
      <input type="hidden" name="uploadNo" value="${upload.uploadNo}">
      <button type="button" id="btn_modify">수정</button>
    </form>
  </div>
  
  <hr>
  
  <!-- 첨부 추가 -->
  
  <!-- 첨부 목록에서 삭제 -->
  
</div>
  
<script>

  
  
</script>
  
<%@ include file="../layout/footer.jsp" %>