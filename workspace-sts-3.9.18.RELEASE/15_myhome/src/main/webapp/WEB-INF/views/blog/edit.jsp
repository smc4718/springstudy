<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="dt" value="<%=System.currentTimeMillis()%>" />

<jsp:include page="../layout/header.jsp">
  <jsp:param value="블로그편집" name="title"/>
</jsp:include>

<style>

  .ck.ck-editor {
    max-width: 1000px;

  }
  .ck-editor__editable {
    min-height: 400px;

  }
  .ck-content {
    color: gray;

  }
</style>

<div>
    <!-- form id="frm_blog_add"는 title의 공백을 검사할 때 사용한다.(지금은 사용 안한 버젼 -->
  <form id="frm_blog_modify" method="post" action="${contextPath}/blog/modifyBlog.do">
    
    <h1 style="text-align: center;">${blog.blogNo}번 블로그 편집</h1>
    
    <div>
      <label for="title">제목</label>
      <input type="text" name="title" id="title" class="form-control" value="${blog.title}">
    </div>
    
    <div>
      <label for="contents">내용</label>
      <textarea name="contents" id="contents">${blog.contents}e</textarea>
    </div>
    
    <div>
      <input type="hidden" name="blogNo" value="${blog.blogNo}"> <!-- 세션에 있는 유저에 있는 유저넘버 -->
      <button type="submit" class="btn btn-success">수정완료</button>
    </div>
    
  </form>

  <script>
    const fnCkeditor = () => {		// 화살표 함수로 변수로 전달하는 방식
  		 
  	  ClassicEditor
  	    .create(document.getElementById('contents'), {	//	getElementById('?')  <- 물음표 자리는 상황에 따라 이름을 바꿔서 써야 한다. 
  		    toolbar: {
  			    items: [
  		        'undo', 'redo',
  		        '|', 'heading',
  		        '|', 'fontfamily', 'fontsize', 'fontColor', 'fontBackgroundColor',
  		        '|', 'bold', 'italic', 'strikethrough', 'subscript', 'superscript', 'code',
  		        '|', 'link', 'uploadImage', 'blockQuote', 'codeBlock',
  		        '|', 'bulletedList', 'numberedList', 'todoList', 'outdent', 'indent'
    		    ],
    		    shouldNotGroupWhenFull: false
    	   },
         heading: {
           options: [
             { model: 'paragraph', title: 'Paragraph', class: 'ck-heading_paragraph' },
             { model: 'heading1', view: 'h1', title: 'Heading 1', class: 'ck-heading_heading1' },
             { model: 'heading2', view: 'h2', title: 'Heading 2', class: 'ck-heading_heading2' },
             { model: 'heading3', view: 'h3', title: 'Heading 3', class: 'ck-heading_heading3' },
             { model: 'heading4', view: 'h4', title: 'Heading 4', class: 'ck-heading_heading4' },
             { model: 'heading5', view: 'h5', title: 'Heading 5', class: 'ck-heading_heading5' },
             { model: 'heading6', view: 'h6', title: 'Heading 6', class: 'ck-heading_heading6' }
           ]
         },
         ckfinder: {
      	   // 업로드 경로 (블로그 경로로 이미지 업로드를 하겠다.)
      	   uploadUrl: '${contextPath}/blog/imageUpload.do'
         }
  	   })
  	   .catch(err => {
  		   console.log(err)
  	   });
  	  
    }
    
    // 함수 호출
    fnCkeditor();
    
  </script>

</div>

<%@ include file="../layout/footer.jsp" %>